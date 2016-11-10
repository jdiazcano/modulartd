package com.jdiazcano.modulartd.keys

import com.badlogic.gdx.Input

class Modifiers(
        val alt: Boolean = false,
        val shift: Boolean = false,
        val control: Boolean = false,
        val command: Boolean = false
        ) {

    override fun toString(): String {
        return buildString {
            if (alt) {
                append(KeyPrinters.alt(), KeyPrinters.glue())
            }
            if (shift) {
                append(KeyPrinters.shift(), KeyPrinters.glue())
            }
            if (control) {
                append(KeyPrinters.control(), KeyPrinters.glue())
            }
            if (command) {
                append(KeyPrinters.command(), KeyPrinters.glue())
            }
        }
    }

    fun toStringWithKey(key: Int) : String {
        return "${toString()}${KeyPrinters.glue()}${Input.Keys.toString(key)}"
    }
}