package io.yeahx4.cpu.logic

import io.yeahx4.cpu.exception.ByteOutOfBoundException
import io.yeahx4.cpu.exception.ByteParsingException
import io.yeahx4.cpu.exception.NegativeByteException
import io.yeahx4.cpu.exception.PositiveByteException
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
class Byte(str: String) {
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
         * Create a new `Byte` instance with decimal value.
         *
         * @param value decimal value of byte. -128 ~ 127
         * @throws ByteOutOfBoundException Given decimal value is out of bound(-128 ~ 127)
         * @since 1.0
         */
        fun fromDec(value: Int): Byte {
            var v = value
            if (value < MIN_VALUE || value > MAX_VALUE)
                throw ByteOutOfBoundException()

            return if (value >= 0) {
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

                Byte(sb.toString())
            } else {
                if (value == MIN_VALUE)
                    return Byte("10000000")

                return fromDec(-value).toNegative()
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
     * Create new `Byte` instance with decimal value.
     * Works same as `Byte.fromDec(Int)`
     *
     * @see Byte.fromDec
     * @since 1.0
     */
    fun constructor(value: Int) = fromDec(value)

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
     * @see Byte.fromDec
     * @since 1.0
     */
    override fun toString(): String =
        "${this.bits.slice(0 until 4).joinToString("")} ${this.bits.slice(4..7).joinToString("")}"

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
    private fun complement(): Byte {
        val b = this.notBits()
        return Op.plus(b, Byte("00000001"))
    }

    /**
     * Check is this byte negative.
     *
     * @see isPositive
     * @since 1.0
     */
    fun isNegative(): Boolean = this.bits[0].value == 1

    /**
     * Check is this byte positive.
     *
     * @see isNegative
     * @since 1.0
     */
    fun isPositive(): Boolean = this.bits[0].value == 0

    /**
     * Create a new byte whose value is negative value of current byte.
     *
     * @since 1.0
     * @throws NegativeByteException Original byte is already negative.
     */
    fun toNegative(): Byte {
        if (this.isNegative())
            throw NegativeByteException()

        val neg = this.complement();
        neg.bits[0].value = 1

        return neg
    }

    /**
     * Reverse operation of `complement`.
     *
     * @see complement
     * @since 1.0
     */
    private fun uncomplement(): Byte {
        return Op.plus(this, Byte("00000001").toNegative())
            .notBits()
    }

    /**
     * Create a new instance whose value is positive of original byte.
     *
     * @since 1.0
     * @see toNegative
     * @throws PositiveByteException Original byte is already positive.
     */
    fun toPositive(): Byte {
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
        return if (this.isPositive()) {
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
