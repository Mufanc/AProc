package xyz.mufanc.aproc.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.util.Properties

@Suppress("unused")
class AProcPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.afterEvaluate {
            project.plugins.withId("com.android.application") {
                val android = project.extensions.getByType(AppExtension::class.java)

                android.applicationVariants.forEach { variant ->
                    variant.outputs.forEach { output ->
                        val assembleTask = variant.assembleProvider.get()
                        assembleTask.doLast {
                            val outputFile = output.outputFile
                            process(outputFile, File(outputFile.parentFile, outputFile.nameWithoutExtension + ".ash"))
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

    private fun process(inputApk: File, outputApk: File) {
        outputApk.parentFile.mkdirs()

        val outputStream = outputApk.outputStream().buffered()

        val buffer = ByteArray(0x1000)
        val template = javaClass.getResourceAsStream("/template.sh")?.use { it.readBytes() }!!

        template.copyInto(buffer, 0, 0, template.size)

        outputStream.write(buffer)
        outputStream.write(inputApk.readBytes())
    }
}