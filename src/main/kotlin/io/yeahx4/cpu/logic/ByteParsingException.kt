package io.yeahx4.cpu.logic

/**
 * Unable to parse String to Byte.
 *
 * @since 1.0
 * @see io.yeahx4.cpu.logic.VByte
 * @see ByteParseExceptionType
 * @param type Specific type of exception.
 */
class ByteParsingException(private val type: ByteParseExceptionType): Exception() {
    /**
     * Specific type of ByteParsingException
     *
     * @since 1.0
     * @see ByteParsingException
     */
    enum class ByteParseExceptionType {
        /**
         * Length of byte is not 8.
         *
         * @since 1.0
         */
        INVALID_LENGTH,

        /**
         * Format of number is invalid.
         * Caused by `NumberFormatException`
         *
         * @see NumberFormatException
         * @since 1.0
         */
        INVALID_NUMBER_FORMAT,

        /**
         * Value of bit is not valid.
         * It should be 0 or 1.
         *
         * @since 1.0
         */
        INVALID_BIT
    }

    /**
     * Message of Exception.
     * This message differs each `type`.
     *
     * @see type
     * @since 1.0
     */
    override val message: String
        get() =
            when(this.type) {
                ByteParseExceptionType.INVALID_LENGTH -> "Invalid length of byte. It should be 8."
                ByteParseExceptionType.INVALID_NUMBER_FORMAT -> "Invalid format of number."
                ByteParseExceptionType.INVALID_BIT -> "Bits can have only 0 and 1."
            }
}
