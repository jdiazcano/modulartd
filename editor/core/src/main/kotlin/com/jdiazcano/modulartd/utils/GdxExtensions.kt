package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.kotcrab.vis.ui.util.dialog.Dialogs
import java.nio.file.Path

/**
 * Shows an error dialog with the text and details given. This will post the dialog to the bus so it will be shown in
 * the screen
 */
fun showErrorDialog(text: String, details: String): Dialogs.DetailsDialog {
    val dialog = createErrorDialog(text, details)
    Bus.post(dialog, BusTopic.NEW_DIALOG)
    return dialog
}

/**
 * Creates an error dialog with a translated Error!
 */
fun createErrorDialog(text: String, details: String): Dialogs.DetailsDialog {
    return Dialogs.DetailsDialog(text, translate("error", "Error"), details)
}

fun FileHandle.resourceType(): ResourceType {
    return if ("wav|ogg|mp3".contains(extension())) {
        ResourceType.SOUND
    } else {
        ResourceType.IMAGE
    }
}

fun FileHandle.isAtlasFile(): Boolean {
    return !extension().equals("atlas", ignoreCase = true) && sibling(nameWithoutExtension() + ".atlas").exists()
}

fun FileHandle.toPath(): Path = file().toPath()

fun Actor.clickListener(listener: (InputEvent?, Float, Float) -> Unit) = addListener(object : ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        listener(event, x, y)
    }
})

fun Actor.changeListener(listener: (ChangeListener.ChangeEvent, Actor) -> Unit) {
    addListener(object : ChangeListener() {
        override fun changed(event: ChangeEvent, actor: Actor) {
            listener(event, actor)
        }
    })
}