package io.yeahx4.cpu.memory.exception

class InvalidMemorySizeException: Exception() {
    override val message: String
        get() = "Size of memory cannot be negative."
}
