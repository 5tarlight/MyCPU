package io.yeahx4.cpu.memory

import io.yeahx4.cpu.util.Duplicatable

interface Storable<T: Duplicatable<T>> {
    fun write(addr: Int, value: T)
    fun get(addr: Int): T?
    fun erase(addr: Int)
    fun getAndErase(addr: Int): T?
    fun getOrElse(addr: Int, otherwise: T): T
}
