package xyz.mufanc.aproc.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.Properties

@Suppress("unused")
class AProcPlugin : Plugin<Project> {

    companion object {
        private const val PAGE_ALIGN = 0x4000  // align to 16kB

        private const val EOCD_SIGN = 0x06054b50  // PK\5\6
        private const val EOCD_BASE_LENGTH = 22
        private const val EOCD_OFFSET_CDFH = 16

        private const val CDFH_SIGN = 0x02014b50  // PK\1\2
        private const val CDFH_BASE_LENGTH = 46
        private const val CDFH_OFFSET_FILE_NAME_LENGTH = 28
        private const val CDFH_OFFSET_EXTRA_FIELD = 30
        private const val CDFH_OFFSET_FILE_COMMENT_LENGTH = 32
        private const val CDFH_OFFSET_LFH = 42

        private const val LFH_SIGN = 0x04034b50  // PK\3\4
    }

    override fun apply(project: Project) {
        project.afterEvaluate {
            project.plugins.withId("com.android.application") {
                val android = project.extensions.getByType(AppExtension::class.java)

                android.applicationVariants.forEach { variant ->
                    variant.outputs.forEach { output ->
                        val assembleTask = variant.assembleProvider.get()
                        assembleTask.doLast {
                            apk2ash(output.outputFile)
                        }
                    }
                }
            }
        }

        val version = javaClass.getResourceAsStream("/META-INF/aproc-plugin.properties")?.use {
            val props = Properties()
            props.load(it)
            props.getProperty("version")
        }

        project.dependencies.apply {
            add("ksp", "xyz.mufanc.aproc:ksp:${version}")
            add("implementation", "xyz.mufanc.aproc:annotation:${version}")
            add("implementation", "xyz.mufanc.aproc:runtime:${version}")
        }
    }

    private fun apk2ash(apk: File) {
        val ash = File(apk.parentFile, apk.nameWithoutExtension + ".ash")

        RandomAccessFile(apk, "r").use { apkRaf ->
            RandomAccessFile(ash, "rw").use { ashRaf ->
                val apkChannel = apkRaf.channel
                val ashChannel = ashRaf.channel

                val apkBuffer = apkChannel.map(FileChannel.MapMode.READ_ONLY, 0, apkChannel.size()).apply { order(ByteOrder.LITTLE_ENDIAN) }
                val ashBuffer = ashChannel.map(FileChannel.MapMode.READ_WRITE, 0, apkChannel.size() + PAGE_ALIGN).apply { order(ByteOrder.LITTLE_ENDIAN) }

                apk2ash(apkBuffer, ashBuffer, apkChannel.size().toInt())
            }
        }
    }

    private fun apk2ash(apk: MappedByteBuffer, ash: MappedByteBuffer, size: Int) {
        // 1. write header
        ash.putInt(LFH_SIGN)
        ash.put(javaClass.getResourceAsStream("/bootstrap.sh")?.use { it.readBytes() }!!)

        // 2. append apk file
        ash.position(PAGE_ALIGN)
        ash.put(apk)

        fun Int.int(): Int {
            return ash.getInt(this)
        }

        fun Int.short(): Int {
            return ash.getShort(this).toInt()
        }

        fun Int.mut(value: Int) {
            ash.putInt(this, value)
        }

        // 3. fix EOCD
        var ptr = PAGE_ALIGN + size - EOCD_BASE_LENGTH

        // assume there's no comment
        assert(ptr.int() == EOCD_SIGN) { "Bad EOCD signature" }

        ptr += EOCD_OFFSET_CDFH

        val tmp = ptr.int() + PAGE_ALIGN
        ptr.mut(tmp)

        // 4. fix CDFH
        ptr = tmp
        assert(ash.getInt(ptr) == CDFH_SIGN) { "Bad CDFH signature" }

        while (ptr.int() == CDFH_SIGN) {
            val ptr2 = ptr + CDFH_OFFSET_LFH
            ptr2.mut(ptr2.int() + PAGE_ALIGN)

            ptr += CDFH_BASE_LENGTH + (ptr + CDFH_OFFSET_FILE_NAME_LENGTH).short() + (ptr + CDFH_OFFSET_EXTRA_FIELD).short() + (ptr + CDFH_OFFSET_FILE_COMMENT_LENGTH).short()
        }
    }
}