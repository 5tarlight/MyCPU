package io.yeahx4.cpu.shell

import io.yeahx4.cpu.logic.VByte

@Deprecated("Obsolete")
class NewVarPayload(args: List<String>): InlineCommandPayload {
    val name: String
    val value: VByte

    init {
        if (args.size != 3)
            throw UnexpectedInlineTokenException(args.joinToString(" "))

        try {
            this.name = args[1]
            this.value = VByte.fromDec(args[2].toInt())
        } catch (ex: NumberFormatException) {
            throw UnexpectedInlineTokenException(args[2])
        }
    }
}
