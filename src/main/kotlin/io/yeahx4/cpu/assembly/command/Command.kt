package io.yeahx4.cpu.assembly.command

import io.yeahx4.cpu.logic.UnsignedByteException

data class Command(
    val type: CommandType,
    val args: List<String>
) {
    companion object {
        fun from(line: String): Command {
            val token = line.trim().split(" ")

            if (token.isEmpty()) {
                throw UnsignedByteException()
            }

            val arg = token.slice(1 until token.size)
            return when (token[0]) {
                "var" -> Command(CommandType.NEW_VAR, arg)
                "quit" -> Command(CommandType.QUIT, arg)
                else -> throw UnexpectedCommandTokenException()
            }
        }
    }

    fun run() {
        println("Command executed: $type, $args")
    }
}
