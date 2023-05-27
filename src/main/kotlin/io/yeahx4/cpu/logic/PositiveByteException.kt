package io.yeahx4.cpu.logic

/**
 * Target byte is already positive.
 *
 * @since 1.0
 */
class PositiveByteException: ByteSignException() {
    override val message: String
        get() = "Byte is already positive"
}
