package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Array
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
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

fun Actor.addChangeListener(listener: (ChangeListener.ChangeEvent?, Actor?) -> Unit) = addListener(object: ChangeListener() {
    override fun changed(event: ChangeEvent?, actor: Actor?) {
        listener(event, actor)
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