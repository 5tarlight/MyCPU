package io.yeahx4.cpu.assembly

class MemoryOutOfBoundException: Exception() {
    override val message: String
        get() = "Memory capacity exceeded."
}
