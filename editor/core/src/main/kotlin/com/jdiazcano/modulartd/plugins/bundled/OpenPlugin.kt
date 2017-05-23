package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.files.FileHandle
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.Game
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.config.Configs
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.jdiazcano.modulartd.utils.createErrorDialog
import com.jdiazcano.modulartd.utils.setSingleFileListener
import com.jdiazcano.modulartd.utils.showErrorDialog
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.file.FileChooser

/**
 * Creates a new project (TD)
 */
class OpenPlugin : Plugin {

    init {
        Bus.register<FileHandle>(FileHandle::class.java, BusTopic.MAP_LOAD) { file ->
            val title = "${file.path()} - ${Configs.editor().baseTitle()}"
            Gdx.graphics.setTitle(title)

            val game: Game = kodein.instance()
            game.gameFolder = file
            Unit // TODO look for a better way to return nothing from a method (In Kotlin it is UNIT)
        }
    }

    override fun getName() = "Open plugin"

    override fun getVersion() = 1

    override fun getMinimumCompatibleVersion() = 1

    override fun getPrefix() = "open"

    override fun onLoad() {}

    override fun onUnload() {}

    @RegisterAction(Menus.FILE)
    fun open() = OpenAction()
}

class OpenAction : Action("file.open", translate("file.open", "Open"), ShortCut(Input.Keys.O, Modifiers(control = true))) {
    override fun perform(stage: StageWrapper) {
        val errorDialog = createErrorDialog("Failed to open", "You can only select one folder when opening or creating a project")
        val chooser = FileChooser(translate("select.project", "Select project"), FileChooser.Mode.OPEN)
        chooser.setWatchingFilesEnabled(true)
        chooser.selectionMode = FileChooser.SelectionMode.DIRECTORIES
        chooser.setSingleFileListener(errorDialog) { file ->
            if (file.child(".itd").exists()) {
                Bus.post(file, BusTopic.MAP_LOAD)
            } else {
                showErrorDialog("Failed to open", "This is not a game folder")
            }

        }

        Bus.post(chooser.fadeIn(), BusTopic.NEW_DIALOG)
    }
}