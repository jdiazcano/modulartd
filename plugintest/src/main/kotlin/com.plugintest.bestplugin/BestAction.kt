package com.plugintest.bestplugin

import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.ui.StageWrapper

class BestAction: Action("best", "Best action!", ShortCut(32, Modifiers(alt = true))) {
    override fun perform(stage: StageWrapper): Unit {
        println("WOOOOO")
    }
}

class AnotherBestAction: Action("anotherb", "Another best action!", ShortCut(32, Modifiers(control = true))) {
    override fun perform(stage: StageWrapper): Unit {
        println("Second bestest action")
    }
}