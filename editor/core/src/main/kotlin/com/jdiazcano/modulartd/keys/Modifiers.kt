package com.jdiazcano.modulartd.keys

import com.badlogic.gdx.Input

class Modifiers(
        val alt: Boolean = false,
        val shift: Boolean = false,
        val control: Boolean = false,
        val command: Boolean = false
        ) {

    override fun toString(): String {
        val keyPrinter = KeyPrinters.printer
        return buildString {
            if (alt) {
                append(keyPrinter.alt(), keyPrinter.glue())
            }
            if (shift) {
                append(keyPrinter.shift(), keyPrinter.glue())
            }
            if (control) {
                append(keyPrinter.control(), keyPrinter.glue())
            }
            if (command) {
                append(keyPrinter.command(), keyPrinter.glue())
            }
        }
    }

    fun toStringWithKey(key: Int) : String {
        val keyPrinter = KeyPrinters.printer
        return "${toString()}${keyPrinter.glue()}${Input.Keys.toString(key)}"
    }
}