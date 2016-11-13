package com.jdiazcano.modulartd.keys

class Modifiers(
        val alt: Boolean = false,
        val shift: Boolean = false,
        val control: Boolean = false,
        val command: Boolean = false
    ) {

    fun toString(keyPrinter: KeyPrinter): String {
        return buildString {
            if (control) {
                append(keyPrinter.control(), keyPrinter.glue())
            }
            if (shift) {
                append(keyPrinter.shift(), keyPrinter.glue())
            }
            if (alt) {
                append(keyPrinter.alt(), keyPrinter.glue())
            }
            if (command) {
                append(keyPrinter.command(), keyPrinter.glue())
            }
        }
    }
}