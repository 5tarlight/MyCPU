package io.yeahx4.cpu.memory

class NullMemoryPointerException: Exception() {
    override val message: String
        get() = "Target address is null."
}
