package io.yeahx4.cpu.memory

abstract class Memory<T: Cloneable>(size: Int): Storable<T> {
    protected val storage: Int

    init {
        if (size <= 0)
            throw InvalidMemorySizeException()

        this.storage = size
    }
}
