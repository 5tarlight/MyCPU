package io.yeahx4.cpu.logic

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class VByteTest {
    @Test
    fun `Create a new byte`() {
        val byte = VByte("00000000")
        assert(byte.toString().isNotEmpty())
    }

    @Test
    fun `Create bytes with string`() {
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

    @Test
    fun `Signed byte out of minimum bound exception`() {
        try {
            VByte.fromDec(-129)
            fail("No exception detected.")
        } catch (_: ByteOutOfBoundException) {}
    }

    @Test
    fun `Signed byte out of maximum bound exception`() {
        try {
            VByte.fromDec(128)
            fail("No exception detected.")
        } catch (_: ByteOutOfBoundException) {}
    }

    @Test
    fun `Unsigned byte out of minimum bound exception`() {
        try {
            VByte.fromDec(-1, false)
            fail("No exception detected.")
        } catch (_: ByteOutOfBoundException) {}
    }

    @Test
    fun `Unsigned byte out of maximum bound exception`() {
        try {
            VByte.fromDec(256, false)
            fail("No exception detected.")
        } catch (_: ByteOutOfBoundException) {}
    }

    @Test
    fun `Create unsigned byte`() {
        val b = VByte("11111111", false)
        assert(b.toDec() == VByte.UNSIGNED_MAX_VALUE)
    }

    @Test
    fun `Unsigned byte from dec`() {
        val b = VByte.fromDec(255, false)
        assert(b.toString(false) == "11111111")
    }

    @Test
    fun `Signed byte negative check`() {
        val b = VByte.fromDec(-1)
        assert(b.isNegative())
        assert(!b.isPositive())
    }

    @Test
    fun `Signed byte positive check`() {
        val b = VByte.fromDec(0)
        assert(!b.isNegative())
        assert(b.isPositive())
    }

    @Test
    fun `Unsigned byte positive check`() {
        val b = VByte.fromDec(0, false)
        assert(!b.isNegative())
        assert(b.isPositive())
    }

    @Test
    fun `Signed byte toPositive should fail when already positive`() {
        try {
            VByte.fromDec(10).toPositive()
            fail("No exception detected")
        } catch (_: PositiveByteException) {}
    }

    @Test
    fun `Signed byte toPositive value check`() {
        val b = VByte.fromDec(10).toNegative()
        assert(b.toDec() == -10)
    }

    @Test
    fun `Signed byte toNegative should fail when already negative`() {
        try {
            VByte.fromDec(-1).toNegative()
            fail("No exception found")
        } catch (_: NegativeByteException) {}
    }

    @Test
    fun `Signed byte toNegative value check`() {
        val b = VByte.fromDec(10).toNegative()
        assert(b.toDec() == -10)
    }

    @Test
    fun `Unsigned byte toNegative should fail`() {
        try {
            VByte.fromDec(10, false).toNegative()
            fail("No exception detected")
        } catch (_: UnsignedByteException) {}
    }
}
