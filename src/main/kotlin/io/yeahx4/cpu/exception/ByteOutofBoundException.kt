package io.yeahx4.cpu.exception

class ByteOutofBoundException: Exception() {
    override val message: String
        get() = "Value of byte should be within -127 ~ 128"
}
