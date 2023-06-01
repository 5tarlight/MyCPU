package io.yeahx4.cpu.assembly

class QuitFailException : Exception() {
    override val message: String
        get() = "Process was not terminated properly."
}
