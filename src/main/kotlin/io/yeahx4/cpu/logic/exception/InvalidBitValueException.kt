package io.yeahx4.cpu.logic.exception

/**
 * Value of bit is not valid.
 * Bits can only have 0 or 1.
 *
 * @since 1.0
 * @param value invalid value
 */
class InvalidBitValueException(private val value: Int): Exception() {
    override val message: String
        get() = "$value is not valid bit. It must be 0 or 1"
}
