package com.jdiazcano.modulartd.keys

class ShortCut(val key: Int = -1,  val modifiers: Modifiers = Modifiers()) {

    override fun toString(): String {
        return super.toString()
    }
}

fun main(args: Array<String>) {
    ShortCut().key
}