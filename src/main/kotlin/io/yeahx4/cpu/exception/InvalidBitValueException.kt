package io.yeahx4.cpu.exception

class InvalidBitValueException(private val value: Int): Exception() {
    override val message: String
        get() = "$value is not valid bit. It must be 0 or 1"
}
