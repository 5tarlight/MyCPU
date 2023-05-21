package io.yeahx4.cpu

import io.yeahx4.cpu.logic.Bit
import io.yeahx4.cpu.logic.Byte
import io.yeahx4.cpu.logic.Op
import io.yeahx4.cpu.shell.InteractiveShell

fun main(args: Array<String>) {
    val b1 = Byte("10000000")
    val b2 = Byte("01111111")
    println(b1.toDec())
    println(b2.toDec())
    println(Op.plus(b1, b2).toDec())
//    if (args.isEmpty()) {
//        InteractiveShell.start(System.`in`)
//    }
}
