package io.yeahx4.cpu.shell

import io.yeahx4.cpu.assembly.command.Command
import io.yeahx4.cpu.assembly.command.CommandType
import io.yeahx4.cpu.assembly.command.UnexpectedCommandTokenException
import java.io.InputStream
import java.util.Scanner

class InteractiveShell {
    companion object {
        fun start(input: InputStream) {
            println("Virtual CPU Interactive Shell")
            println("Tip : use \"help\" command to check usage.")
            println("Tip : \"quit\" to exit.\n")
            val scanner = Scanner(input)

            while (true) {
                print("> ")
                val command = scanner.nextLine().trim()
                val startTime = System.currentTimeMillis()

                if (command.isEmpty())
                    continue

                try {
                    val cmd = Command.from(command)

                    if (cmd.type == CommandType.QUIT) {
                        println("Gracefully terminating interactive shell...")
                        break
                    }

                    cmd.run()
                } catch (ex: UnexpectedCommandTokenException) {
                    println("Error : ${ex.message}")
                    continue
                }

                val endTime = System.currentTimeMillis() - startTime
                println("Done in ${endTime.toDouble() / 1000} seconds.\n")
            }
        }
    }
}
