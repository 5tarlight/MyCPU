package io.yeahx4.cpu

import io.yeahx4.cpu.logic.Byte
import io.yeahx4.cpu.logic.Op

fun main(args: Array<String>) {
    println(Byte.fromDec(127))
    println(Byte.fromDec(-128).toPositive())
    println(Op.plus(Byte.fromDec(127), Byte.fromDec(1)))
}
