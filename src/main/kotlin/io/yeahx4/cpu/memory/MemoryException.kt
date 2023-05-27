package io.yeahx4.cpu.memory

open class MemoryException: Exception() {
    override val message: String
        get() = "Unknown memory exception occured."
}
