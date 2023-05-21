package io.yeahx4.cpu.shell

data class InlineCommand(
    val type: InlineCommandType,
    val payload: InlineCommandPayload
)
