package io.yeahx4.cpu.assembly.voltile

import io.yeahx4.cpu.assembly.MemoryOutOfBoundException
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

    private fun getFreeAddr(length: Int = 1): VByte {
        var index = 0
        var destination = -1

        while (index <= VByte.MAX_VALUE && destination == -1) {
            if (this.ram.get(index) == null) {
                var good = true
                var offset = 1

                while (length != 1 && good) {
                    if (
                        index + offset > VByte.MAX_VALUE
                        || this.ram.get(index + offset) != null
                    ) {
                        good = false
                    }

                    if (offset + 1 == length) {
                        break
                    }

                    offset++
                }

                if (good) {
                    destination = if (length == 1)
                        index
                    else
                        index + offset

                    break
                }
            }

            index++
        }

        // No Free Memory Space
        if (index > VByte.MAX_VALUE && destination == -1)
            throw MemoryOutOfBoundException()

        return VByte.fromDec(destination)
    }

    fun assign(name: String, value: VByte) {
        val addr = getFreeAddr()

        if (nameTable.containsKey(name))
            throw VariableRedeclaredException(name)

        nameTable[name] = addr.duplicate()
        ram.write(addr.duplicate(), value.duplicate())
    }

    fun assign(name: String, value: Int) {
        this.assign(name, VByte.fromDec(value))
    }

    fun modify(name: String, value: VByte) {
        if (!nameTable.containsKey(name))
            throw VariableNotFoundException(name)

        this.ram.modify(this.nameTable[name]!!, value.duplicate())
    }

    fun modify(name: String, value: Int) {
        this.modify(name, VByte.fromDec(value))
    }

    fun read(name: String): VByte {
        if (!nameTable.containsKey(name))
            throw VariableNotFoundException(name)

        return this.ram.get(this.nameTable[name]!!)!!
    }

    fun delete(name: String) {
        if (!nameTable.containsKey(name))
            throw VariableNotFoundException(name)

        this.ram.erase(this.nameTable[name]!!)
        this.nameTable.remove(name)
    }

    fun destroy() {
        this.ram.clear()
        this.nameTable.clear()
    }
}
