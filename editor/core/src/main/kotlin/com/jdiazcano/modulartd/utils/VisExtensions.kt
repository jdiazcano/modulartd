package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Array
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.InputDialogAdapter
import com.kotcrab.vis.ui.widget.VisDialog
import com.kotcrab.vis.ui.widget.file.FileChooser
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter

/**
 * Small extensions for the VisUI that allows us using Kotlin features
 */
fun TabbedPane.addListener(listener: (Tab) -> Unit) = addListener(object: TabbedPaneAdapter() {
    override fun switchedTab(tab: Tab?) {
        tab?.let { listener(tab) }
    }
})

fun FileChooser.setSelectedListener(listener: (Array<FileHandle>) -> Unit) = setListener(object : FileChooserAdapter() {
    override fun selected(files: Array<FileHandle>) = listener(files)
})

fun FileChooser.setSingleFileListener(dialog: VisDialog, listener: (FileHandle) -> Unit) = setListener(object : FileChooserAdapter() {
    override fun selected(files: Array<FileHandle>) {
        if (files.size == 0 || files.size > 1) {
            Bus.post(dialog, BusTopic.NEW_DIALOG)
        } else {
            listener(files[0])
        }
    }
})

inline fun input(title: String, fieldTitle: String, crossinline action: (String) -> Unit) {
    val dialog = Dialogs.InputDialog(title, fieldTitle, true, null, object : InputDialogAdapter() {
        override fun finished(input: String) = action(input)
    })
    Bus.post(dialog, BusTopic.NEW_DIALOG)
}

inline fun confirm(title: String, question: String, crossinline actionYes: () -> Unit, crossinline actionNo: () -> Unit) {
    val dialog = Dialogs.ConfirmDialog<Int>(title, question, arrayOf("Yes", "No", "Cancel"), arrayOf(1, 2, 3)) {
        when (it) {
            1 -> actionYes()
            2 -> actionNo()
            3 -> {} // We just do nothing since it's cancel!
        }
    }

    Bus.post(dialog, BusTopic.NEW_DIALOG)
}