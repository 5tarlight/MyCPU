package io.yeahx4.cpu.shell

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
                    payload = NewVarPayload(command)
                }
                "quit", "exit" -> {
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
