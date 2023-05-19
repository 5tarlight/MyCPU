package io.yeahx4.cpu.logic

import io.yeahx4.cpu.exception.ByteParsingError
import kotlin.math.pow

class Byte(str: String) {
    private val bits = mutableListOf(
        Bit(0),
        Bit(0),
        Bit(0),
        Bit(0),

        Bit(0),
        Bit(0),
        Bit(0),
        Bit(0)
    )
    val length = 8

    init {
        if (str.length != 8)
            throw ByteParsingError(ByteParsingError.ByteParseExceptionType.INVALID_LENGTH)

        str
            .toCharArray()
            .map { it.toString() }
            .map {
                try {
                    return@map it.toInt()
                } catch (_: NumberFormatException) {
                    throw ByteParsingError(ByteParsingError.ByteParseExceptionType.INVALID_NUMBER_FORMAT)
                }
            }
            .forEachIndexed { i, v ->
                if (v != 0 && v != 1)
                    throw ByteParsingError(ByteParsingError.ByteParseExceptionType.INVALID_BIT)

                if (bits[i].value != v)
                    bits[i].value = v
            }
    }

    override fun toString(): String =
        "${this.bits.slice(0 until 4).joinToString("")} ${this.bits.slice(4..7).joinToString("")}"

    fun toList(): List<Bit> = bits.toList()

    fun toDec(): Int {
        return if (this.bits[0].value == 0) {
            this.bits
                .slice(1..7)
                .mapIndexed { i, b ->
                    b.value * 2.0.pow(7.0 - i).toInt()
                }
                .sum()
        } else {
            // TODO : Minus
            -1
        }
    }
}