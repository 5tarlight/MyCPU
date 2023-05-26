package io.yeahx4.cpu.memory

interface Storable<T: Cloneable> {
    fun getStorage(): Int
    fun write(addr: Int, value: T)
    fun read(addr: Int): T?
    fun erase(addr: Int)
    fun getAndErase(addr: Int): T?
    fun getOrElse(addr: Int, otherwise: T): T
}
