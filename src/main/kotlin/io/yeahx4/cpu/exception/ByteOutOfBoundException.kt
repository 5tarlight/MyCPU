package io.yeahx4.cpu.exception

class ByteOutOfBoundException: Exception() {
    override val message: String
        get() = "Value of byte should be within -127 ~ 128"
}
