package io.yeahx4.cpu.exception

class ByteParsingException(private val type: ByteParseExceptionType): Exception() {
    enum class ByteParseExceptionType {
        INVALID_LENGTH,
        INVALID_NUMBER_FORMAT,
        INVALID_BIT
    }

    override val message: String
        get() =
            when(this.type) {
                ByteParseExceptionType.INVALID_LENGTH -> "Invalid length of byte. It should be 8."
                ByteParseExceptionType.INVALID_NUMBER_FORMAT -> "Invalid format of number."
                ByteParseExceptionType.INVALID_BIT -> "Bits can have only 0 and 1."
            }
}
