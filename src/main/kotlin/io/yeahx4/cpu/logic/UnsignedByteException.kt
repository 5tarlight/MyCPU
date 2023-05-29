package io.yeahx4.cpu.logic

class UnsignedByteException: Exception() {
    override val message: String
        get() = "Unable to transfer unsigned byte into negative value."
}