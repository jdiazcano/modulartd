package com.jdiazcano.modulartd.keys

import com.badlogic.gdx.Input
import io.kotlintest.specs.StringSpec

class ShortCutTest : StringSpec() {
    init {
        "It should create good looking to strings for mac!" {
            val printer = MacKeyPrinter()
            val shortCuts = table(
                    headers("shortcut", "result"),
                    row(ShortCut(Input.Keys.A, Modifiers(alt = true)), "⌥A"),
                    row(ShortCut(Input.Keys.W, Modifiers(alt = true)), "⌥W"),
                    row(ShortCut(Input.Keys.COMMA, Modifiers(command = true)), "⌘,"),
                    row(ShortCut(Input.Keys.A, Modifiers(shift = true)), "⇧A"),
                    row(ShortCut(Input.Keys.A, Modifiers(control = true)), "⌃A"),
                    row(ShortCut(Input.Keys.F5, Modifiers(alt = true)), "⌥F5"),
                    row(ShortCut(Input.Keys.A, Modifiers(command = true, alt = true)), "⌥⌘A"),
                    row(ShortCut(Input.Keys.A, Modifiers(true, true, true, true)), "⌃⇧⌥⌘A"),
                    row(ShortCut(Input.Keys.NUM_9, Modifiers(alt = true, shift = true)), "⇧⌥9"),
                    row(ShortCut(Input.Keys.BACKSPACE, Modifiers(command = true, shift = true)), "⇧⌘⌫")

            )
            forAll(shortCuts) { shortcut, result ->
                shortcut.toString(printer) shouldEqual result
            }
        }

        "It should create good looking to strings for windows!" {
            val printer = WindowsKeyPrinter()
            val shortCuts = table(
                    headers("shortcut", "result"),
                    row(ShortCut(Input.Keys.A, Modifiers(alt = true)), "Alt+A"),
                    row(ShortCut(Input.Keys.W, Modifiers(alt = true)), "Alt+W"),
                    row(ShortCut(Input.Keys.A, Modifiers(shift = true)), "Shift+A"),
                    row(ShortCut(Input.Keys.A, Modifiers(control = true)), "Ctrl+A"),
                    row(ShortCut(Input.Keys.F5, Modifiers(alt = true)), "Alt+F5"),
                    row(ShortCut(Input.Keys.NUM_9, Modifiers(alt = true, shift = true)), "Shift+Alt+9")

            )
            forAll(shortCuts) { shortcut, result ->
                shortcut.toString(printer) shouldEqual result
            }
        }
    }
}