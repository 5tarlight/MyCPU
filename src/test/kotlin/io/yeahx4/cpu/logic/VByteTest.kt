package io.yeahx4.cpu.logic

import org.junit.jupiter.api.Test

class VByteTest {
    @Test
    fun `Create a new byte`() {
        val byte = VByte("00000000")
        assert(byte.toString().isNotEmpty())
    }

    @Test
    fun `Create bytes with string`() {
        // TODO : Automatic test
        assert(VByte("00000010").toDec() == 2)
    }

    @Test
    fun `Create bytes with int`() {
        for (i in VByte.MIN_VALUE..VByte.MAX_VALUE) {
            val byte = VByte.fromDec(i)
            assert(byte.toDec() == i)
            assert(VByte(byte.toString().split(" ").joinToString("")).toDec() == i)
        }
    }
}
