package io.yeahx4.cpu.exception

class PositiveByteException: ByteSignException() {
    override val message: String
        get() = "Byte is already positive"
}
