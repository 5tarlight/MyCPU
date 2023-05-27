package io.yeahx4.cpu.memory.structure

import io.yeahx4.cpu.logic.VByte
import io.yeahx4.cpu.memory.AddressOutOfBoundException
import io.yeahx4.cpu.memory.Memory
import io.yeahx4.cpu.util.Duplicatable

class Ram<T: Duplicatable<T>>: Memory<T>(VByte.UNSIGNED_MAX_VALUE) {
    private val data = mutableMapOf<Int, T>()

    private fun checkBound(addr: Int) {
        if (addr < 0 || addr >= storage) {
            throw AddressOutOfBoundException()
        }
    }

    override fun write(addr: Int, value: T) {
        checkBound(addr)
        data[addr] = value.duplicate()
    }

    override fun get(addr: Int): T? {
        checkBound(addr)
        return data[addr]
    }

    override fun erase(addr: Int) {
        checkBound(addr)
        data.remove(addr)
    }

    override fun getAndErase(addr: Int): T? {
        checkBound(addr)
        return data.remove(addr)
    }

    override fun getOrElse(addr: Int, otherwise: T): T {
        checkBound(addr)
        return data[addr] ?: otherwise
    }
}
