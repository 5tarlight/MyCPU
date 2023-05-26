package io.yeahx4.cpu.memory

import io.yeahx4.cpu.memory.exception.InvalidMemorySizeException

abstract class VRam<T: Cloneable>(size: Int): Storable<T> {
    protected val storage: Int

    init {
        if (size <= 0)
            throw InvalidMemorySizeException()

        this.storage = size
    }
}
