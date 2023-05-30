package io.yeahx4.cpu.memory

import io.yeahx4.cpu.logic.VByte
import io.yeahx4.cpu.memory.structure.ByteRam
import org.junit.jupiter.api.Test

class ByteRamTest {
    private fun b(value: Int = 10): VByte {
        return VByte.fromDec(value)
    }

    @Test
    fun `Add a item and read`() {
        val ram = ByteRam<VByte>()

        for (i in 0..VByte.MAX_VALUE)
            ram.write(i, VByte.fromDec(i))

        for (i in 0..VByte.MAX_VALUE)
            assert(ram.get(i) == VByte.fromDec(i))
    }

    @Test
    fun `Erase value`() {
        val ram = ByteRam<VByte>()
        ram.write(10, VByte.fromDec(10))
        ram.erase(10)

        assert(ram.get(10) == null)
    }

    @Test
    fun `Clear ram`() {
        val ram = ByteRam<VByte>()

        for (i in 0..VByte.MAX_VALUE)
            ram.write(i, VByte.fromDec(i))

        ram.clear()

        for (i in 0..VByte.MAX_VALUE)
            assert(ram.get(i) == null)
    }

    @Test
    fun `Modify value`() {
        val ram = ByteRam<VByte>()
        ram.write(10, VByte.fromDec(10))
        ram.modify(10, VByte.fromDec(20))

        assert(ram.get(10)!!.toDec() == 20)
    }
}
