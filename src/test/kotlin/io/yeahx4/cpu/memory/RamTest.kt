package io.yeahx4.cpu.memory

import io.yeahx4.cpu.logic.VByte
import io.yeahx4.cpu.memory.structure.Ram
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class RamTest {
    @Test
    fun `Create a new RAM instance`() {
        val ram = Ram<VByte>()
        assert(ram.storage == VByte.UNSIGNED_MAX_VALUE)
    }

    private fun initRam(): Ram<VByte> {
        val ram = Ram<VByte>()
        val b = VByte.fromDec(10)
        ram.write(0x10, b)

        return ram
    }

    @Test
    fun `Insert and read VByte value`()  {
        val ram = initRam()
        val b = VByte.fromDec(10)
        val read = ram.get(0x10)

        assert(b.toDec() == read?.toDec())
    }

    @Test
    fun `Read null value`() {
        val ram = initRam()
        val nullable = ram.get(0x11)

        assert(nullable == null)
    }

    @Test
    fun `If index is out of bound, it should throw`() {
        val ram = initRam()

        try {
            ram.write(0xffffff, VByte("00000000"))
            fail("Exception not detected")
        } catch (_: AddressOutOfBoundException) {}
    }
}
