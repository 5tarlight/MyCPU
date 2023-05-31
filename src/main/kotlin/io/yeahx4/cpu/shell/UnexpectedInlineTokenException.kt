package io.yeahx4.cpu.shell

@Deprecated("Moved to io.yeahx4.cpu.assembly.command.UnexpectedCommandTokenException")
class UnexpectedInlineTokenException(private val token: String): Exception() {
    override val message: String
        get() = "Unexpected token $token"
}
