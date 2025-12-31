package xyz.mufanc.aproc.runtime

import android.os.Build
import org.joor.Reflect
import java.util.Properties

object AProcHelper {

    private val APROC_APK = System.getenv("APROC_APK")!!

    fun fixLoadLibrary() {
        val targetSdk = javaClass.getResourceAsStream("/META-INF/aproc.properties")?.use {
            val props = Properties()
            props.load(it)
            props.getProperty("targetSdk").toInt()
        }

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
