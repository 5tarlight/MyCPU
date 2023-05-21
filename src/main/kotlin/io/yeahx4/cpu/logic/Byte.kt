package io.yeahx4.cpu.logic

import io.yeahx4.cpu.exception.ByteParsingError
import io.yeahx4.cpu.exception.NegativeByteException
import io.yeahx4.cpu.exception.PositiveByteException
import java.lang.StringBuilder
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

    private fun notBits(): Byte {
        val sb = StringBuilder(this.bits[0].value.toString())

        this.bits
            .slice(1..7)
            .stream()
            .map { it.toInt() }
            .map { if (it == 0) 1 else 0 }
            .forEach { sb.append(it) }

        return Byte(sb.toString())
    }

    fun complement(): Byte {
        val b = this.notBits()
        return Op.plus(b, Byte("00000001"))
    }

    fun isNegative(): Boolean = this.bits[0].value == 1

    fun toNegative(): Byte {
        if (this.isNegative())
            throw NegativeByteException()

        val neg = this.complement();
        neg.bits[0].value = 1

        return neg
    }

    fun uncomplement(): Byte {
        return Op.plus(this, Byte("00000001").toNegative())
            .notBits()
    }

    fun toPositive(): Byte {
        if (!this.isNegative())
            throw PositiveByteException()

        val b = this.uncomplement()
        b.bits[0].value = 0

        return b
    }

    fun toDec(): Int {
        return if (!this.isNegative()) {
            this.bits
                .slice(1..7)
                .mapIndexed { i, b ->
                    b.value * 2.0.pow(6.0 - i).toInt()
                }
                .sum()
        } else {
            val pos = this.toPositive()
            val dec = -pos.toDec()

            if (dec == 0)
                -128
            else
                dec
        }
    }
}
