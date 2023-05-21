package io.yeahx4.cpu.exception

/**
 * Target byte is already negative.
 *
 * @since 1.0
 */
class NegativeByteException: ByteSignException() {
    override val message: String
        get() = "Byte is already negative"
}
