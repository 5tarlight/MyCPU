package io.yeahx4.cpu.memory

import io.yeahx4.cpu.util.Duplicatable

interface Storable<K, T: Duplicatable<T>> {
    fun write(addr: K, value: T)
    fun get(addr: K): T?
    fun erase(addr: K)
    fun getAndErase(addr: K): T?
    fun getOrElse(addr: K, otherwise: T): T
}
