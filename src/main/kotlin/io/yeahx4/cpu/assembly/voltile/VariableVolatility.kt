package io.yeahx4.cpu.assembly.voltile

import io.yeahx4.cpu.assembly.VariableNotFoundException
import io.yeahx4.cpu.logic.VByte
import io.yeahx4.cpu.memory.structure.ByteRam
import io.yeahx4.cpu.assembly.VariableRedeclaredException

class VariableVolatility private constructor(){
    companion object {
        private val instance = VariableVolatility()

        fun getInstance() = instance
    }

    private val ram: ByteRam<VByte> = ByteRam()
    // VariableName, VariableAddress
    private val nameTable = mutableMapOf<String, VByte>()

    fun assign(name: String, addr: VByte, value: VByte) {
        if (nameTable.containsKey(name))
            throw VariableRedeclaredException(name)

        nameTable[name] = addr.duplicate()
        ram.write(addr.duplicate(), value.duplicate())
    }

    fun modify(name: String, value: VByte) {
        if (!nameTable.containsKey(name))
            throw VariableNotFoundException(name)

        this.ram.modify(this.nameTable[name]!!, value.duplicate())
    }

    fun read(name: String): VByte {
        if (!nameTable.containsKey(name))
            throw VariableNotFoundException(name)

        return this.ram.get(this.nameTable[name]!!)!!
    }

    fun destroy() {
        this.ram.clear()
        this.nameTable.clear()
    }
}
