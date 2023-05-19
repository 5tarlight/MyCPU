package io.yeahx4.cpu

import io.yeahx4.cpu.logic.Bit
import io.yeahx4.cpu.logic.Byte
import io.yeahx4.cpu.logic.Op
import io.yeahx4.cpu.shell.InteractiveShell

fun main(args: Array<String>) {
    val b1 = Byte("00001111")
    val b2 = Byte("00001111")
    println(Op.plus(b1, b2))

//    if (args.isEmpty()) {
//        InteractiveShell.start(System.`in`)
//    }
}
