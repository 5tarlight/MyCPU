package io.yeahx4.cpu.shell

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
                val command = scanner.nextLine().trim().split(" ")

                val startTime = System.currentTimeMillis()

                if (command.isEmpty())
                    continue

                if (command[0] == "quit") {
                    println("Gracefully terminating interactive shell...")
                    break
                }

                // TODO : Execute command
                println(command.joinToString(" "))
                val endTime = System.currentTimeMillis() - startTime

                println("Done in ${endTime.toDouble() / 1000} seconds.\n")
            }
        }
    }
}
