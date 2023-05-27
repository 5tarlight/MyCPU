package io.yeahx4.cpu.memory

class AddressOutOfBoundException: MemoryException() {
    override val message: String
        get() = "Address is out of bound."
}
