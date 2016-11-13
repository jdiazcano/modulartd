package com.jdiazcano.modulartd.keys

/**
 *
 */
abstract class Action(val shortCut: ShortCut) {
    abstract fun perform(): Nothing
}