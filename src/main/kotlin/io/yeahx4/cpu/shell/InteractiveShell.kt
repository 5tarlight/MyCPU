package io.yeahx4.cpu.shell

import io.yeahx4.cpu.shell.exception.UnexpectedInlineTokenException
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

                try {
                    val cmd = InlineCommandParser.parse(command)

                    if (cmd.type == InlineCommandType.QUIT) {
                        println("Gracefully terminating interactive shell...")
                        break
                    }

                    println(cmd.payload.toString())
                } catch (ex: UnexpectedInlineTokenException) {
                    println("Error : ${ex.message}")
                    continue
                }

                val endTime = System.currentTimeMillis() - startTime
                println("Done in ${endTime.toDouble() / 1000} seconds.\n")
            }
        }
    }
}
