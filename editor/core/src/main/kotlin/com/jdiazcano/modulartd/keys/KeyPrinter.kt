package com.jdiazcano.modulartd.keys

import com.jdiazcano.modulartd.utils.OSUtils

interface KeyPrinter {
    fun alt() : String
    fun shift() : String
    fun control() : String
    fun command() : String
    fun cReturn() : String
    fun enter() : String
    fun delete() : String
    fun forwardDelete() : String
    fun escape() : String
    fun rightArrow() : String
    fun leftArrow() : String
    fun downArrow() : String
    fun upArrow() : String
    fun pageUp() : String
    fun pageDown() : String
    fun home() : String
    fun end() : String
    fun clear() : String
    fun tab() : String
    fun shiftTab() : String

    fun glue() : String
}

class MacKeyPringer : KeyPrinter {
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

object KeyPrinters: KeyPrinter by if (OSUtils.mac()) MacKeyPringer() else WindowsKeyPrinter()