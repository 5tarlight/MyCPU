package io.yeahx4.cpu.exception

class NegativeByteException: ByteSignException() {
    override val message: String
        get() = "Byte is already negative"
}
