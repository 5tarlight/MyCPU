package io.yeahx4.cpu.logic

/**
 * Value is not valid.
 * Value should be between -127 and 128
 *
 * @since 1.0
 */
class ByteOutOfBoundException(private val signed: Boolean): Exception() {
    override val message: String
        get() {
            return if (signed)
                "Value of signed byte should be within -128 ~ 127"
            else
                "Value of unsigned byte should be within 0 ~ 255"
        }
}
