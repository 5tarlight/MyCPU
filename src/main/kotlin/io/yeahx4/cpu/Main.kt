package io.yeahx4.cpu

import io.yeahx4.cpu.logic.Bit
import io.yeahx4.cpu.shell.InteractiveShell

fun main(args: Array<String>) {
    val zero = Bit(0)
    val one = Bit(1)

    println(zero)
    println(one)

    if (args.isEmpty()) {
        InteractiveShell.start(System.`in`)
    }
}
