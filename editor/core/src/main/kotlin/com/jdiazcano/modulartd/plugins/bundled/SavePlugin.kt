package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.badlogic.gdx.files.FileHandle
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.Game
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.config.Configs
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.io.FileIO
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.jdiazcano.modulartd.utils.createErrorDialog
import com.jdiazcano.modulartd.utils.setSingleFileListener
import com.jdiazcano.modulartd.utils.text
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.file.FileChooser

/**
 *
 */
class SavePlugin(private val saveAs: Boolean) : Plugin {
    override fun getName() = "Save ${saveAs.text("As")} plugin"

    override fun getVersion() = 1

    override fun getMinimumCompatibleVersion() = 1

    override fun getPrefix() = "save${saveAs.text("As")}"

    override fun onLoad() {}

    override fun onUnload() {}

    @RegisterAction(Menus.FILE)
    fun save() = SaveAction(saveAs)
}

class SaveAction(private val saveAs: Boolean) :
        Action("file.save${saveAs.text("As")}", "Save${saveAs.text(" As")}",
                ShortCut(Input.Keys.S, Modifiers(control = true, shift = saveAs))) {

    override fun perform(stage: StageWrapper) {
        val game = kodein.instance<Game>()
        if (saveAs) {
            val errorDialog = createErrorDialog("Failed to save", "You can only select one folder when opening or creating a project")
            val chooser = FileChooser(translate("select.project"), FileChooser.Mode.SAVE)
            chooser.setWatchingFilesEnabled(true)
            chooser.selectionMode = FileChooser.SelectionMode.DIRECTORIES
            chooser.setSingleFileListener(errorDialog) { file ->
                // 2TODO this will throw an exception if the map hasn't been created so I must check if the map
                // TODO has been created before saving the game and take action if needed.
                saveGame(file)
                game.gameFolder = file // The folder has changed since we now saved the map in a new folder

                Bus.post(LoadMapEvent(file, false), BusTopic.MAP_LOAD)
            }

            Bus.post(chooser.fadeIn(), BusTopic.NEW_DIALOG)
        } else { // if (game.dirty) { TODO this will only be available when the dirty option is enabled and we can perform changes to the map
            saveGame(game.gameFolder)
        }

    }

    private fun saveGame(file: FileHandle) {
        val itdFolder = file.child(Configs.editor.gameConfigFolder())
        val gameFile = itdFolder.child(Configs.editor.gameFileName())
        val map = kodein.instance<Map>()
        val io = kodein.instance<FileIO<Map>>()
        io.write(map, gameFile)
    }

}