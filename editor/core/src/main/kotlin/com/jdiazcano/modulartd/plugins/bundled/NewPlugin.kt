package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.actions.SeparatorPlace
import com.jdiazcano.modulartd.plugins.ui.StageWrapper

/**
 * Creates a new project (TD)
 */
class NewPlugin : Plugin {
    override fun getName() = "New plugin"

    override fun getVersion() = 1

    override fun getMinimumCompatibleVersion() = 1

    override fun getPrefix() = "new"

    override fun onLoad() {}

    override fun onUnload() {}

    @RegisterAction(Menus.FILE)
    fun new() = NewAction()
}

class NewAction : Action("file.new", "New", ShortCut(Input.Keys.N, Modifiers(control = true)), SeparatorPlace.BELOW) {
    override fun perform(stage: StageWrapper) {
        // TODO now here we must create a new project and do cool things
    }
}