package io.yeahx4.cpu.assembly.voltile

import io.yeahx4.cpu.assembly.VariableNotFoundException
import io.yeahx4.cpu.logic.VByte
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class VariableVolatilityTest {
    @BeforeEach
    fun `Clear memory`() {
        VariableVolatility
            .getInstance()
            .destroy()
    }

    @Test
    fun `Assign Value and Read`() {
        val volatility = VariableVolatility.getInstance()

        volatility.assign("a", VByte.fromDec(10))
        volatility.assign("b", VByte.fromDec(10))
        volatility.assign("c", VByte.fromDec(10))

        assert(volatility.read("a").toDec() == 10)
    }

    @Test
    fun `Modify value`() {
        val volatility = VariableVolatility.getInstance()

        volatility.assign("a", 10)
        volatility.modify("a", 20)

        assert(volatility.read("a").toDec() == 20)
    }

    @Test
    fun `Delete value`() {
        val volatility = VariableVolatility.getInstance()

        volatility.assign("a", 10)
        volatility.assign("b", 20)
        volatility.assign("c", 30)

        volatility.delete("b")

        volatility.assign("d", 40)
        volatility.assign("e", 50)

        assert(volatility.read("a").toDec() == 10)
        assert(volatility.read("c").toDec() == 30)
        assert(volatility.read("d").toDec() == 40)
        assert(volatility.read("e").toDec() == 50)

        try {
            volatility.read("b")
            fail("No exception detected.")
        } catch (_: VariableNotFoundException) {}
    }
}
