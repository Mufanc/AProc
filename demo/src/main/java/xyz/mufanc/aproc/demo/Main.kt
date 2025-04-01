package xyz.mufanc.aproc.demo

import xyz.mufanc.aproc.annotation.ShellEntry

@ShellEntry
object Main {

    @JvmStatic
    fun main(vararg args: String) {
        println("args: [${args.joinToString(", ")}]")
    }
}
