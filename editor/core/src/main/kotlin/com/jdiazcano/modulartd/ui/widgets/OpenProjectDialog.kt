package com.jdiazcano.modulartd.ui.widgets

import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.config.Configs
import com.jdiazcano.modulartd.plugins.bundled.LoadMapEvent
import com.jdiazcano.modulartd.plugins.bundled.createBaseMapFiles
import com.jdiazcano.modulartd.utils.changeListener
import com.jdiazcano.modulartd.utils.createErrorDialog
import com.jdiazcano.modulartd.utils.setSingleFileListener
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.VisDialog
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.file.FileChooser

object OpenProjectDialog : VisDialog(translate("select.project")) {

    private val open: VisTextButton
    private val newMap: VisTextButton

    init {
        isModal = true

        val errorDialog = createErrorDialog("Failed to open", "You can only select one folder when opening or creating a project")
        val chooser = FileChooser(translate("select.project"), FileChooser.Mode.OPEN)
        chooser.setWatchingFilesEnabled(true)
        chooser.selectionMode = FileChooser.SelectionMode.DIRECTORIES
        chooser.setSingleFileListener(errorDialog) { file ->
            val itdFolder = file.child(Configs.editor.gameConfigFolder())
            val existingMap = itdFolder.exists()
            if (!existingMap) {
                createBaseMapFiles(file)
            }

            Bus.post(LoadMapEvent(file, existingMap), BusTopic.MAP_LOAD)

            fadeOut()
        }

        open = VisTextButton(translate("file.open"))
        open.changeListener { _, _ ->
            Bus.post(chooser.fadeIn(), BusTopic.NEW_DIALOG)
        }
        newMap = VisTextButton(translate("file.new"))
        newMap.changeListener { _, _ ->
            Bus.post(chooser.fadeIn(), BusTopic.NEW_DIALOG)
        }

        add(open)
        add(newMap)
        isModal = true
        pack()
        centerWindow()
    }

    fun select() = Bus.post(fadeIn(), BusTopic.NEW_DIALOG)

}