package com.jdiazcano.modulartd.keys

import com.badlogic.gdx.Input.Keys
import com.jdiazcano.modulartd.utils.OSUtils

/**
 * Interface that defines a KeyPrinter, a KeyPrinter is meant to be like a toString for shortcuts, this will be the
 * methods that the implementation of the shortcut will have to call in order to get the right toString on Windows and
 * Mac
 */
interface KeyPrinter {
    fun alt(): String
    fun shift(): String
    fun control(): String
    fun command(): String
    fun cReturn(): String
    fun enter(): String
    fun delete(): String
    fun forwardDelete(): String
    fun escape(): String
    fun rightArrow(): String
    fun leftArrow(): String
    fun downArrow(): String
    fun upArrow(): String
    fun pageUp(): String
    fun pageDown(): String
    fun home(): String
    fun end(): String
    fun clear(): String
    fun tab(): String
    fun shiftTab(): String

    fun glue(): String

    fun toString(key: Int): String {
        return when(key) {
            Keys.ALT_LEFT -> alt()
            Keys.ALT_RIGHT -> alt()
            Keys.SHIFT_LEFT -> shift()
            Keys.SHIFT_RIGHT -> shift()
            Keys.CONTROL_LEFT -> control()
            Keys.CONTROL_RIGHT -> control()
            Keys.BACKSPACE -> delete() // Same as Keys.DEL
            Keys.FORWARD_DEL -> forwardDelete()
            Keys.RIGHT -> rightArrow()
            Keys.LEFT -> leftArrow()
            Keys.UP -> upArrow()
            Keys.DOWN -> downArrow()
            Keys.CLEAR -> clear()
            Keys.END -> end()
            Keys.TAB -> tab()
            Keys.HOME -> home()
            Keys.PAGE_DOWN -> pageDown()
            Keys.PAGE_UP -> pageUp()
            Keys.ESCAPE -> escape()
            else -> Keys.toString(key)
        }
    }

}

class MacKeyPrinter : KeyPrinter {
    override fun alt() = "⌥"
    override fun shift() = "⇧"
    override fun control() = "⌃"
    override fun command() = "⌘"
    override fun cReturn() = "↩"
    override fun enter() = "⌤"
    override fun delete() = "⌫"
    override fun forwardDelete() = "⌦"
    override fun escape() = "⎋"
    override fun rightArrow() = "→"
    override fun leftArrow() = "←"
    override fun downArrow() = "↓"
    override fun upArrow() = "↑"
    override fun pageUp() = "⇞"
    override fun pageDown() = "⇟"
    override fun home() = "↖"
    override fun end() = "↘"
    override fun clear() = "⌧"
    override fun tab() = "⇥"
    override fun shiftTab() = "⇤"

    override fun glue() = ""
}

class WindowsKeyPrinter : KeyPrinter {
    override fun alt() = "Alt"
    override fun shift() = "Shift"
    override fun control() = "Ctrl"
    override fun command() = "Cmd" // This does not exist!
    override fun cReturn() = "Ret"
    override fun enter() = "Enter"
    override fun delete() = "Del"
    override fun forwardDelete() = "Supr"
    override fun escape() = "Esc"
    override fun rightArrow() = "RArrow"
    override fun leftArrow() = "LArrow"
    override fun downArrow() = "DArrow"
    override fun upArrow() = "UArrow"
    override fun pageUp() = "PgUp"
    override fun pageDown() = "PgDown"
    override fun home() = "Home"
    override fun end() = "End"
    override fun clear() = "Clear"
    override fun tab() = "Tab"
    override fun shiftTab() = "STab"

    override fun glue() = "+"
}

object KeyPrinters {
    val keyPrinter = if (OSUtils.mac()) MacKeyPrinter() else WindowsKeyPrinter()

    fun print(modifiers: Modifiers): String {
        return buildString {
            if (modifiers.control) {
                append(keyPrinter.control(), keyPrinter.glue())
            }
            if (modifiers.shift) {
                append(keyPrinter.shift(), keyPrinter.glue())
            }
            if (modifiers.alt) {
                append(keyPrinter.alt(), keyPrinter.glue())
            }
            if (modifiers.command) {
                append(keyPrinter.command(), keyPrinter.glue())
            }
        }
    }

    fun print(shortCut: ShortCut): String {
        return "${print(shortCut.modifiers)}${keyPrinter.toString(shortCut.key)}"
    }
}