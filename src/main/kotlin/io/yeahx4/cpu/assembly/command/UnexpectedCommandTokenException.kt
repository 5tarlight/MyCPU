package io.yeahx4.cpu.assembly.command

class UnexpectedCommandTokenException: Exception() {
    override val message: String
        get() = "Unexpected token."
}