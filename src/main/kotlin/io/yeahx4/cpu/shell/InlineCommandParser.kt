package io.yeahx4.cpu.shell

import io.yeahx4.cpu.shell.exception.UnexpectedInlineTokenException

class InlineCommandParser {
    companion object {
        fun parse(command: List<String>): InlineCommand {
            if (command.isEmpty())
                throw UnexpectedInlineTokenException("")

            val type: InlineCommandType
            val payload: InlineCommandPayload
            when (command[0]) {
                "var" -> {
                    type = InlineCommandType.NEW_VAR
                    payload = NewVarPayload()
                }
                "quit" -> {
                    type = InlineCommandType.QUIT
                    payload = QuitPayload()
                }
                else -> {
                    throw UnexpectedInlineTokenException(command[0])
                }
            }

            return InlineCommand(type, payload)
        }
    }
}
