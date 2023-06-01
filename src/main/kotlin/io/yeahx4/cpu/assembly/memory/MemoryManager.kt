package io.yeahx4.cpu.assembly.memory

import io.yeahx4.cpu.assembly.voltile.VariableVolatility

class MemoryManager private constructor(private val ram: VariableVolatility){
    companion object {
        private val instance = MemoryManager(VariableVolatility.getInstance())

        fun getInstance() = instance
    }

    fun declare(name: String, value: Int) {
        this.ram.assign(name, value)
    }

    fun get(name: String): Int {
        return ram.read(name).toDec()
    }

    fun free(name: String) {
        ram.delete(name)
    }
}
