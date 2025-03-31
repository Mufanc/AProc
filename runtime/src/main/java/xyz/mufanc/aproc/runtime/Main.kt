package xyz.mufanc.aproc.runtime

import android.system.Os
import android.system.OsConstants
import org.joor.Reflect
import java.io.File
import java.util.Properties

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val apk = Os.getenv("APROC_TMP")
        val props = javaClass.getResourceAsStream("/META-INF/aproc.properties")?.use {
            val props = Properties()
            props.load(it)
            props
        }

        val fd = Os.open(apk, OsConstants.O_RDONLY, 0)

        File(apk).delete()
        Os.setenv("APROC_TMP", "/dev/fd/${Reflect.on(fd).get<Int>("descriptor")}", true)

        Class.forName(props!!.getProperty("entry")).getMethod("main", Array<String>::class.java).invoke(null, args)
    }
}
