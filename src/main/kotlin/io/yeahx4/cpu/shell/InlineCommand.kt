package io.yeahx4.cpu.shell

@Deprecated("Moved to io.yeahx4.cpu.assembly.command.Command")
data class InlineCommand(
    val type: InlineCommandType,
    val payload: InlineCommandPayload
) {
    fun execute() {
        when (type) {
            InlineCommandType.QUIT -> {}
            InlineCommandType.NEW_VAR -> {
                val newVarPayload = this.payload as NewVarPayload
                println("${newVarPayload.name} = ${newVarPayload.value}")
            }
        }
    }
}
