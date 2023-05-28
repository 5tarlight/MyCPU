package io.yeahx4.cpu.logic

import io.yeahx4.cpu.util.Duplicatable
import kotlin.math.pow

/**
 * Byte class. This performs same features like traditional byte type.
 * Plus and minus operation is supported via `Op`
 *
 * @see Op
 * @since 1.0
 * @param str binary String
 * @throws ByteParsingException Unable to parse String to byte
 */
class VByte(str: String, private val signed: Boolean = true): Duplicatable<VByte> {
    companion object {
        /**
         * Minimum value of signed byte.
         * -128
         */
        const val MIN_VALUE = -128

        /**
         * Maximum value of signed byte.
         * 127
         */
        const val MAX_VALUE = 127

        /**
         * Maximum value of unsigned byte.
         */
        const val UNSIGNED_MAX_VALUE = 255

        /**
         * Create a new `Byte` instance with decimal value.
         *
         * @param value decimal value of byte. -128 ~ 127
         * @throws ByteOutOfBoundException Given decimal value is out of bound(-128 ~ 127)
         * @since 1.0
         */
        fun fromDec(value: Int, signed: Boolean = true): VByte {
            var v = value
            if (signed) {
                if (value < MIN_VALUE || value > MAX_VALUE) {
                    throw ByteOutOfBoundException(true)
                }
            } else {
                if (value < 0 || value > UNSIGNED_MAX_VALUE) {
                    throw ByteOutOfBoundException(false)
                }
            }

            return if (signed) {
                if (value >= 0) {
                    // Signed Positive value parsing
                    var digit = 6
                    val sb = StringBuilder("0")

                    while (digit >= 0) {
                        val pos = 2.0.pow(digit.toDouble()).toInt()
                        if (v >= pos) {
                            sb.append("1")
                            v -= pos
                        } else {
                            sb.append("0")
                        }

                        digit--
                    }

                    VByte(sb.toString())
                } else {
                    // Signed negative value parsing
                    if (value == MIN_VALUE)
                        return VByte("10000000")

                    fromDec(-value).toNegative()
                }
            } else {
                var digit = 7
                val sb = StringBuilder()

                while (digit >= 0) {
                    val pos = 2.0.pow(digit.toDouble()).toInt()

                    if (v >= pos) {
                        sb.append("1")
                        v -= pos
                    } else {
                        sb.append("0")
                    }

                    digit--
                }

                VByte(sb.toString(), false)
            }
        }
    }

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

    /**
     * Length of bits.
     *
     * @since 1.0
     */
    val length = 8

    init {
        if (str.length != length)
            throw ByteParsingException(ByteParsingException.ByteParseExceptionType.INVALID_LENGTH)

        str
            .toCharArray()
            .map { it.toString() }
            .map {
                try {
                    return@map it.toInt()
                } catch (_: NumberFormatException) {
                    throw ByteParsingException(ByteParsingException.ByteParseExceptionType.INVALID_NUMBER_FORMAT)
                }
            }
            .forEachIndexed { i, v ->
                if (v != 0 && v != 1)
                    throw ByteParsingException(ByteParsingException.ByteParseExceptionType.INVALID_BIT)

                if (bits[i].value != v)
                    bits[i].value = v
            }
    }

    /**
     * Converts byte into binary `String`.
     *
     * # Example
     * ```kt
     * println(Byte(10).toString()) // 00001010
     * println(Byte(-1).toString()) // 11111111
     * ```
     *
     * @see String
     * @see VByte.fromDec
     * @since 1.0
     */
    override fun toString(): String {
        return this.toString(true)
    }

    fun toString(deco: Boolean): String {
        return if (deco)
            "${this.bits.slice(0 until 4).joinToString("")} ${this.bits.slice(4..7).joinToString("")}"
        else
            this.bits.joinToString("")
    }

    /**
     * Convert byte into immutable list of bits.
     *
     * @since 1.0
     */
    fun toList(): List<Bit> = bits.toList()

    /**
     * Perform not operation(`~`) except for MSB.
     *
     * * MSB : Most Significant Bit, The Sign Bit, first bit.
     *
     * @since 1.0
     */
    private fun notBits(): VByte {
        return if (signed) {
            val sb = StringBuilder(this.bits[0].value.toString())

            this.bits
                .slice(1..7)
                .map { it.toInt() }
                .map { if (it == 0) 1 else 0 }
                .forEach { sb.append(it) }

            VByte(sb.toString())
        } else {
            val sb = StringBuilder()

            this.bits
                .map { it.toInt() }
                .map { if (it == 0) 1 else 0 }
                .forEach { sb.append(it) }

            VByte(sb.toString())
        }
    }

    /**
     * Complement of `2`.
     * Except for MSB.
     *
     * * Complment of `2` is same as complement of `1` + 1
     *
     * @see uncomplement
     * @see notBits
     * @since 1.0
     */
    private fun complement(): VByte {
        val b = this.notBits()
        return Op.plus(b, VByte("00000001"))
    }

    /**
     * Check is this byte negative.
     *
     * @see isPositive
     * @since 1.0
     */
    fun isNegative(): Boolean = this.bits[0].value == 1 && signed

    /**
     * Check is this byte positive.
     *
     * @see isNegative
     * @since 1.0
     */
    fun isPositive(): Boolean = this.bits[0].value == 0 || !signed

    /**
     * Create a new byte whose value is negative value of current byte.
     *
     * @since 1.0
     * @throws NegativeByteException Original byte is already negative.
     */
    fun toNegative(): VByte {
        if (!signed) {
            throw UnsignedByteException()
        } else if (this.isNegative()) {
            throw NegativeByteException()
        }

        val neg = this.complement()
        neg.bits[0].value = 1

        return neg
    }

    /**
     * Reverse operation of `complement`.
     *
     * @see complement
     * @since 1.0
     */
    private fun uncomplement(): VByte {
        return Op.plus(this, VByte("00000001").toNegative())
            .notBits()
    }

    /**
     * Create a new instance whose value is positive of original byte.
     *
     * @since 1.0
     * @see toNegative
     * @throws PositiveByteException Original byte is already positive.
     */
    fun toPositive(): VByte {
        if (this.isPositive())
            throw PositiveByteException()

        val b = this.uncomplement()
        b.bits[0].value = 0

        return b
    }

    /**
     * Convert binary value into decimal.
     *
     * @since 1.0
     */
    fun toDec(): Int {
        return if (signed) {
            if (this.isPositive()) {
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
        } else {
            this.bits
                .mapIndexed { i, b ->
                    b.value * 2.0.pow(7.0 - i).toInt()
                }
                .sum()
        }
    }

    /**
     * Clone itself to new instance.
     */
    override fun duplicate(): VByte {
        return fromDec(this.toDec(), signed)
    }
}
