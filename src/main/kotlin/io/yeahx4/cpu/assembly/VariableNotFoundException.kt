package io.yeahx4.cpu.assembly

class VariableNotFoundException(private val name: String): Exception() {
    override val message: String
        get() = "Variable $name is not found."
}
