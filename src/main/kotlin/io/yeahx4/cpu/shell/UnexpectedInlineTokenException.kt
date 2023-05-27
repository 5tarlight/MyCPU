package io.yeahx4.cpu.shell

class UnexpectedInlineTokenException(private val token: String): Exception() {
    override val message: String
        get() = "Unexpected token $token"
}
