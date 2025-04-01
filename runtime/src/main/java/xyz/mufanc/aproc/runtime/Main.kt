package xyz.mufanc.aproc.runtime

import android.os.Build
import org.joor.Reflect
import java.util.Properties

object Main {

    private val APROC_APK = System.getenv("APROC_APK")!!

    @JvmStatic
    fun main(args: Array<String>) {
        setup()

        val props = javaClass.getResourceAsStream("/META-INF/aproc.properties")?.use {
            val props = Properties()
            props.load(it)
            props
        }

        Class.forName(props!!.getProperty("entry")).getMethod("main", Array<String>::class.java).invoke(null, args)
    }

    private fun setup() {
        val targetSdk = ManifestHelper().parseTargetSdk() ?: Build.VERSION.SDK_INT

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Reflect.onClass("com.android.internal.os.ClassLoaderFactory").call(
                "createClassloaderNamespace",
                javaClass.classLoader,
                targetSdk,
                "${APROC_APK}!/lib/${Build.SUPPORTED_ABIS[0]}",
                null,
                true,
                APROC_APK,
                null
            )
        } else {
            Reflect.onClass("com.android.internal.os.ClassLoaderFactory").call(
                "createClassloaderNamespace",
                javaClass.classLoader,
                targetSdk,
                "${APROC_APK}!/lib/${Build.SUPPORTED_ABIS[0]}",
                null,
                true,
                APROC_APK,
            )
        }
    }
}
