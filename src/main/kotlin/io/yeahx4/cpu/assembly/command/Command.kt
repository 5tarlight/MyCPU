package io.yeahx4.cpu.assembly.command

import io.yeahx4.cpu.assembly.NumberParameterFormatException
import io.yeahx4.cpu.assembly.QuitFailException
import io.yeahx4.cpu.assembly.VariableNotFoundException
import io.yeahx4.cpu.assembly.VariableRedeclaredException
import io.yeahx4.cpu.assembly.memory.MemoryManager
import io.yeahx4.cpu.logic.UnsignedByteException
import io.yeahx4.cpu.logic.VByte
import io.yeahx4.cpu.memory.NullMemoryPointerException

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

    private fun checkArgsLength(size: List<String>, least: Int) {
        if (size.size < least) {
            throw InsufficientParameterException()
        }
    }

    /**
     * @throws InsufficientParameterException Number of parameter is insufficient.
     * @throws UnimplementedCommandException That command is not implemented.
     * @throws NumberParameterFormatException Given parameter is not numeric, but
     * required numeric value.
     * @throws VariableRedeclaredException Variable with same name is already declared.
     * @throws VariableNotFoundException name of the variable is unknown
     */
    fun run() {
        try {
            when (this.type) {
                CommandType.NEW_VAR -> {
                    checkArgsLength(args, 2)
                    val name = args[0]
                    val value = args[1].toInt()

                    MemoryManager
                        .getInstance()
                        .declare(name, value)
                }
                CommandType.QUIT -> throw QuitFailException()
                else -> throw UnimplementedCommandException()
            }
        } catch (_: NumberFormatException) {
            throw NumberParameterFormatException()
        }
    }
}
