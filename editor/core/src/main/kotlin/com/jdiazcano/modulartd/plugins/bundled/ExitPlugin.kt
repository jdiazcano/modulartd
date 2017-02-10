package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction

/**
 * This plugin will exit the JVM (Program)
 */
class ExitPlugin : Plugin {
    override fun getName() = "Exit plugin"

    override fun getVersion() = 1

    override fun getMinimumCompatibleVersion() = 1

    override fun getPrefix() = "exit"

    override fun onLoad() {}

    override fun onUnload() {}

    @RegisterAction(Menus.FILE)
    fun exit() = ExitAction()
}

class ExitAction : Action("file.exit", "Exit", ShortCut(Input.Keys.Q, Modifiers(control = true))) {
    override fun perform() {
        System.exit(0)
    }
}