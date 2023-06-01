package io.yeahx4.cpu.assembly

class NumberParameterFormatException : Exception() {
    override val message: String
        get() = "Not valid number"
}
