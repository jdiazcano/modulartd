package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.badlogic.gdx.files.FileHandle
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.actions.SeparatorPlace
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.jdiazcano.modulartd.utils.createErrorDialog
import com.jdiazcano.modulartd.utils.setSingleFileListener
import com.jdiazcano.modulartd.utils.showErrorDialog
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.file.FileChooser

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
        val errorDialog = createErrorDialog("Failed to open", "You can only select one folder when opening or creating a project")
        val chooser = FileChooser(translate("select.project", "Select project"), FileChooser.Mode.OPEN)
        chooser.setWatchingFilesEnabled(true)
        chooser.selectionMode = FileChooser.SelectionMode.DIRECTORIES
        chooser.setSingleFileListener(errorDialog) { file ->
            val itdFolder = file.child(".itd")
            if (!itdFolder.exists()) {
                createBaseMapFiles(file)

                Bus.post(file, BusTopic.MAP_LOAD)
            } else {
                showErrorDialog("Failed to create", "This is an already created map, please open it.")
            }

        }

        Bus.post(chooser.fadeIn(), BusTopic.NEW_DIALOG)
    }

}

fun createBaseMapFiles(baseFile: FileHandle) {
    val itdFolder = baseFile.child(".itd")
    val soundsFolder = baseFile.child("sounds")
    val imagesFolder = baseFile.child("images")
    itdFolder.mkdirs()
    soundsFolder.mkdirs()
    imagesFolder.mkdirs()
}
