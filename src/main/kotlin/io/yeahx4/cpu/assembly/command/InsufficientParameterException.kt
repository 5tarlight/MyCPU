package io.yeahx4.cpu.assembly.command

class InsufficientParameterException: Exception() {
    override val message: String
        get() = "Number of given parameter is insufficient."
}
