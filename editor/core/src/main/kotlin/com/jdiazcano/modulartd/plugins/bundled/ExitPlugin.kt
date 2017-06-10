package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.Game
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.actions.SeparatorPlace
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.jdiazcano.modulartd.utils.confirm

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

class ExitAction : Action("file.exit", "Exit", ShortCut(Input.Keys.Q, Modifiers(control = true)), SeparatorPlace.ABOVE) {
    override fun perform(stage: StageWrapper) {
        val game = kodein.instance<Game>()
        if (game.dirty) {
            confirm("Unsaved changes", "There are unsaved changes, do you want to save them before exiting?", {
                saveGame(game.gameFolder)
                System.exit(0)
            }, {
                System.exit(0)
            })
        } else {
            System.exit(0)
        }
    }
}