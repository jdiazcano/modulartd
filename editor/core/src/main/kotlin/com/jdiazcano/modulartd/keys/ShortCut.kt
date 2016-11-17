package com.jdiazcano.modulartd.keys

class ShortCut(val key: Int = -1,  val modifiers: Modifiers = Modifiers()) {

    fun toString(keyPrinter: KeyPrinter): String {
        return "${modifiers.toString(keyPrinter)}${keyPrinter.toString(key)}"
    }

}