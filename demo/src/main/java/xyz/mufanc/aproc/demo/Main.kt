package xyz.mufanc.aproc.demo

import xyz.mufanc.aproc.annotation.AProcEntry
import xyz.mufanc.aproc.runtime.AProcHelper

@AProcEntry
object Main {

    @JvmStatic
    fun main(vararg args: String) {
        AProcHelper.fixLoadLibrary()
        System.loadLibrary("demo_a")
        println("args: [${args.joinToString(", ")}]")
    }
}
