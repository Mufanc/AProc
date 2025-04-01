package xyz.mufanc.aproc.runtime

import java.util.Properties

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val props = javaClass.getResourceAsStream("/META-INF/aproc.properties")?.use {
            val props = Properties()
            props.load(it)
            props
        }

        Class.forName(props!!.getProperty("entry")).getMethod("main", Array<String>::class.java).invoke(null, args)
    }
}
