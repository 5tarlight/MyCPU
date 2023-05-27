package io.yeahx4.cpu.logic

import kotlin.Byte

class Bit(value: Int) {
    var value: Int = -1
        set(value) {
            if (!(value == 0 || value == 1))
                throw InvalidBitValueException(value)

            field = value
        }

    init {
        if (!(value == 0 || value == 1))
            throw InvalidBitValueException(value)

        this.value = value
    }

    override fun toString(): String {
        return this.value.toString()
    }

    fun toByte(): Byte {
        return this.value.toByte()
    }

    fun toShort(): Short {
        return this.value.toShort()
    }

    fun toInt(): Int {
        return this.value
    }

    fun toLong(): Long {
        return this.value.toLong()
    }

    fun isPos(): Boolean {
        return this.value == 1
    }

    fun isNeg(): Boolean {
        return this.value == 0
    }
}
