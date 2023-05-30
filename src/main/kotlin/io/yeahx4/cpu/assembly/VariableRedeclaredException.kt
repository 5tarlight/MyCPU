package io.yeahx4.cpu.assembly

class VariableRedeclaredException(private val name: String): Exception() {
    override val message: String
        get() = "Variable $name is redeclared."
}
