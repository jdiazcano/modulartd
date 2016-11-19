package com.plugintest.bestplugin

import com.jdiazcano.modulartd.plugins.AbstractPlugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.RegisterAction

class BestPluginEver : AbstractPlugin() {
    override fun getPrefix(): String {
        return "test"
    }

    override fun getName(): String = "Nice name"

    override fun getVersion(): Int = 1

    override fun getMinimumCompatibleVersion(): Int = 1

    override fun onLoad() {
        println("LUL KEK")
    }

    @RegisterAction
    fun test(): Action {
        return BestAction()
    }
}