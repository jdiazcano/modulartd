package com.plugintest.bestplugin

import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.actions.Action

class BestAction: Action("best", "Best action!", ShortCut(32, Modifiers(alt = true)), "MM0") {
    override fun perform(): Unit {
        println("WOOOOO")
    }
}