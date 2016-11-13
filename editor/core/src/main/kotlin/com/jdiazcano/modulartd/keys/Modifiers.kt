package com.jdiazcano.modulartd.keys

/**
 * Modifier class that will define the modifiers for a Key, this class is used inside ShortCut to see which modifiers a
 * key can have
 */
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