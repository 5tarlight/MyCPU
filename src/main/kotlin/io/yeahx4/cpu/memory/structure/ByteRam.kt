package io.yeahx4.cpu.memory.structure

import io.yeahx4.cpu.logic.VByte
import io.yeahx4.cpu.memory.AddressOutOfBoundException
import io.yeahx4.cpu.memory.Memory
import io.yeahx4.cpu.util.Duplicatable

class ByteRam<T: Duplicatable<T>>: Memory<VByte, T>(VByte.UNSIGNED_MAX_VALUE) {
    private val data = mutableMapOf<VByte, T>()

    private fun checkBound(addr: VByte) {
        if (addr.toDec() < 0 || addr.toDec() >= storage) {
            throw AddressOutOfBoundException()
        }
    }

    override fun write(addr: VByte, value: T) {
        checkBound(addr)
        data[addr] = value.duplicate()
    }

    fun write(addr: Int, value: T) {
        this.write(VByte.fromDec(addr), value)
    }

    override fun get(addr: VByte): T? {
        checkBound(addr)
        return data[addr]
    }

    fun get(addr: Int): T? {
        return this.get(VByte.fromDec(addr))
    }

    override fun erase(addr: VByte) {
        checkBound(addr)
        data.remove(addr)
    }

    fun erase(addr: Int) {
        this.erase(VByte.fromDec(addr))
    }

    override fun getAndErase(addr: VByte): T? {
        checkBound(addr)
        return data.remove(addr)
    }

    fun getAndErase(addr: Int): T? {
        return this.getAndErase(VByte.fromDec(addr))
    }

    override fun getOrElse(addr: VByte, otherwise: T): T {
        checkBound(addr)
        return data.remove(addr) ?: otherwise
    }

    fun getOrElse(addr: Int, otherwise: T): T {
        return this.getOrElse(VByte.fromDec(addr), otherwise)
    }

    override fun clear() {
        this.data.clear()
    }
}
