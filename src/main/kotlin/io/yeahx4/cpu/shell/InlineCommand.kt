package io.yeahx4.cpu.shell

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
