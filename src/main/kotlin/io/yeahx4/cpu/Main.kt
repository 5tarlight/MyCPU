package io.yeahx4.cpu

import io.yeahx4.cpu.shell.InteractiveShell

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        InteractiveShell.start(System.`in`)
    }
}
