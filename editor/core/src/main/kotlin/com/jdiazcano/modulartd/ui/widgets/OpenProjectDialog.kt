package com.jdiazcano.modulartd.ui.widgets

import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.plugins.bundled.createBaseMapFiles
import com.jdiazcano.modulartd.utils.addChangeListener
import com.jdiazcano.modulartd.utils.createErrorDialog
import com.jdiazcano.modulartd.utils.setSingleFileListener
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.VisDialog
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.file.FileChooser

object OpenProjectDialog : VisDialog(translate("select.project", "Select project")) {

    private val open: VisTextButton
    private val newMap: VisTextButton

    init {
        isModal = true

        val errorDialog = createErrorDialog("Failed to open", "You can only select one folder when opening or creating a project")
        val chooser = FileChooser(translate("select.project", "Select project"), FileChooser.Mode.OPEN)
        chooser.setWatchingFilesEnabled(true)
        chooser.selectionMode = FileChooser.SelectionMode.DIRECTORIES
        chooser.setSingleFileListener(errorDialog) { file ->
            val itdFolder = file.child(".itd")
            if (!itdFolder.exists()) {
                createBaseMapFiles(itdFolder)
            }

            Bus.post(file, BusTopic.MAP_LOAD)

            fadeOut()
        }

        open = VisTextButton(translate("file.open", "Open"))
        open.addChangeListener { _, _ ->
            Bus.post(chooser.fadeIn(), BusTopic.NEW_DIALOG)
        }
        newMap = VisTextButton(translate("file.new", "New map"))
        newMap.addChangeListener { _, _ ->
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