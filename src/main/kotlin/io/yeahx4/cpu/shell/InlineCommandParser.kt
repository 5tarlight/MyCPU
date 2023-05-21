package io.yeahx4.cpu.shell

import io.yeahx4.cpu.shell.exception.UnexpectedInlineTokenException

class InlineCommandParser {
    companion object {
        fun parse(command: String) {
            val token = command.trim().split(" ")

            if (token.isEmpty())
                throw UnexpectedInlineTokenException("")
        }
    }
}
