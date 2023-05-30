package io.yeahx4.cpu.assembly.voltile

import io.yeahx4.cpu.assembly.MemoryOutOfBoundException
import io.yeahx4.cpu.assembly.VariableNotFoundException
import io.yeahx4.cpu.logic.VByte
import io.yeahx4.cpu.memory.structure.ByteRam
import io.yeahx4.cpu.assembly.VariableRedeclaredException

/**
 * Automatic `VByte` based memory (similar to RAM)
 * management system.
 * Every constructor is private and this class is
 * designed to be used in Singleton.
 * Note that this class share one instance with
 * every reference, which means adding, reading,
 * modifying value will affect every reference of
 * this class.
 *
 * @see VByte
 * @since 1.0
 */
class VariableVolatility private constructor(){
    companion object {
        /**
         * Shared instance of itself
         */
        private val instance = VariableVolatility()

        /**
         * Get instance of `VariableVolatility`.
         * This instance is shared in universe.
         */
        fun getInstance() = instance
    }

    private val ram: ByteRam<VByte> = ByteRam()
    // VariableName, VariableAddress
    private val nameTable = mutableMapOf<String, VByte>()

    private fun getFreeAddr(length: Int = 1): VByte {
        var index = 0
        var destination = -1

        while (index <= VByte.MAX_VALUE && destination == -1) {
            if (this.ram.get(index) == null) {
                var good = true
                var offset = 1

                while (length != 1 && good) {
                    if (
                        index + offset > VByte.MAX_VALUE
                        || this.ram.get(index + offset) != null
                    ) {
                        good = false
                    }

                    if (offset + 1 == length) {
                        break
                    }

                    offset++
                }

                if (good) {
                    destination = if (length == 1)
                        index
                    else
                        index + offset

                    break
                }
            }

            index++
        }

        // No Free Memory Space
        if (index > VByte.MAX_VALUE && destination == -1)
            throw MemoryOutOfBoundException()

        return VByte.fromDec(destination)
    }

    /**
     * Assign new named variable with value.
     * An address where new variable will be stored is
     * automatically determined by system.
     *
     * @see assign
     */
    fun assign(name: String, value: VByte) {
        val addr = getFreeAddr()

        if (nameTable.containsKey(name))
            throw VariableRedeclaredException(name)

        nameTable[name] = addr.duplicate()
        ram.write(addr.duplicate(), value.duplicate())
    }

    /**
     * Assign new named variable with value
     * in decimal.
     * Wrapper of assign(String, VByte)
     *
     * @see assign
     */
    fun assign(name: String, value: Int) {
        this.assign(name, VByte.fromDec(value))
    }

    /**
     * Alter value of variable which is already declared.
     *
     * @throws VariableNotFoundException name of the variable is unknown.
     */
    fun modify(name: String, value: VByte) {
        if (!nameTable.containsKey(name))
            throw VariableNotFoundException(name)

        this.ram.modify(this.nameTable[name]!!, value.duplicate())
    }

    /**
     * Alter value of variable with decimal value.
     * Wrapper of modify(String, VByte)
     *
     * @throws VariableNotFoundException name of the variable is unknown.
     */
    fun modify(name: String, value: Int) {
        this.modify(name, VByte.fromDec(value))
    }

    /**
     * Read value of variable with name.
     *
     * @throws VariableNotFoundException name of the variable is unknown.
     */
    fun read(name: String): VByte {
        if (!nameTable.containsKey(name))
            throw VariableNotFoundException(name)

        return this.ram.get(this.nameTable[name]!!)!!
    }

    /**
     * Remove variable from the memory.
     * After deletion, memory is freed and ready for
     * new variable assignment.
     *
     * @throws VariableNotFoundException name of the variable is unknown.
     */
    fun delete(name: String) {
        if (!nameTable.containsKey(name))
            throw VariableNotFoundException(name)

        this.ram.erase(this.nameTable[name]!!)
        this.nameTable.remove(name)
    }

    /**
     * Destroy the memory and delete everything.
     */
    fun destroy() {
        this.ram.clear()
        this.nameTable.clear()
    }
}
