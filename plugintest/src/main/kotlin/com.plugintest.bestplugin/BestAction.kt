package com.plugintest.bestplugin

import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.actions.Action

class BestAction: Action("best", "Best action!", ShortCut(32, Modifiers(alt = true))) {
    override fun perform(): Unit {
        println("WOOOOO")
    }
}

class AnotherBestAction: Action("anotherb", "Another best action!", ShortCut(32, Modifiers(control = true))) {
    override fun perform(): Unit {
        println("Second bestest action")
    }
}