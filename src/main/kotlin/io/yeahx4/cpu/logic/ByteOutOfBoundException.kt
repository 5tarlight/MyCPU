package io.yeahx4.cpu.logic

/**
 * Value is not valid.
 * Value should be between -127 and 128
 *
 * @since 1.0
 */
class ByteOutOfBoundException: Exception() {
    override val message: String
        get() = "Value of byte should be within -127 ~ 128"
}
