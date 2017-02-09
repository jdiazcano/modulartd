package com.plugintest.bestplugin

import com.jdiazcano.modulartd.plugins.AbstractPlugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.Preferences
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

    @RegisterAction(Menus.FILE)
    fun test() = BestAction()

    @RegisterAction("best")
    fun secondTest() = AnotherBestAction()

    @Preferences
    fun preferences() = BestPreferences()

}