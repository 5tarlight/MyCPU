package io.yeahx4.cpu.logic

import io.yeahx4.cpu.exception.InvalidBitValueException

class Bit(value: Byte) {
    var value: Byte = -1
        set(value) {
            if (!(value.toInt() == 0 || value.toInt() == 1))
                throw InvalidBitValueException(value)

            field = value
        }

    init {
        if (!(value.toInt() == 0 || value.toInt() == 1))
            throw InvalidBitValueException(value)

        this.value = value
    }

    override fun toString(): String {
        return this.value.toString()
    }

    fun toByte(): Byte {
        return this.value
    }

    fun toShort(): Short {
        return this.value.toShort()
    }

    fun toInt(): Int {
        return this.value.toInt()
    }

    fun toLong(): Long {
        return this.value.toLong()
    }

    fun isPos(): Boolean {
        return this.value.toInt() == 1
    }

    fun isNeg(): Boolean {
        return this.value.toInt() == 0
    }
}
