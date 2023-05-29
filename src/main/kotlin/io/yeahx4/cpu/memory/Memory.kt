package io.yeahx4.cpu.memory

import io.yeahx4.cpu.util.Duplicatable

abstract class Memory<K, T: Duplicatable<T>>(size: Int): Storable<K, T> {
    val storage: Int

    init {
        if (size <= 0)
            throw InvalidMemorySizeException()

        this.storage = size
    }

    abstract fun clear();
}
