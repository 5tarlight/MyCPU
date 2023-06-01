package io.yeahx4.cpu.assembly.command

class UnimplementedCommandException : Exception() {
    override val message: String
        get() = "This command is not implemented."
}