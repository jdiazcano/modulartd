package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.VisScrollPane
import com.kotcrab.vis.ui.widget.VisTable
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
    return Dialogs.DetailsDialog(text, translate("error"), details)
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

inline fun Actor.clickListener(crossinline listener: (InputEvent?, Float, Float) -> Unit) = addListener(object : ClickListener() {
    override fun clicked(event: InputEvent?, x: Float, y: Float) {
        listener(event, x, y)
    }
})

inline fun Actor.changeListener(crossinline listener: (ChangeListener.ChangeEvent, Actor) -> Unit) {
    addListener(object : ChangeListener() {
        override fun changed(event: ChangeEvent, actor: Actor) {
            listener(event, actor)
        }
    })
}

inline fun Actor.sneakyChange(crossinline listener: () -> Unit) {
    addListener(object : ChangeListener() {
        override fun changed(event: ChangeEvent, actor: Actor) {
            try {
                listener()
            } catch (exception: Exception) {
                // If we can't execute the listener then we just ignore everything and it won't be saved
            }
        }
    })
}

/**
 * Load internal assets. This part is used to get the internal asset icons that will be located
 * inside the assets/icons folder. Since this is just internal use I don't really have to take
 * into account non existant filenames or whatever! (That is cool)
 */
private val internalAssets = AssetManager(InternalFileHandleResolver())
fun icon(name: String, size: Int = 24): Texture {
    val completeFileName = "icons/${name}_${size}.png"
    if (!internalAssets.isLoaded(completeFileName)) {
        internalAssets.load(completeFileName, Texture::class.java)
        internalAssets.finishLoading()
    }
    return internalAssets.get(completeFileName, Texture::class.java)
}

fun Texture.asDrawable() = TextureRegionDrawable(TextureRegion(this))

/**
 * Converts a collection to a LibGDX array. This is used when handling ComboBoxes (SelectBox in libgdx)
 * because they get a libgdx array as parameter.
 */
inline fun <reified E> Collection<E>.asGdxArray(): com.badlogic.gdx.utils.Array<E> {
    val array = com.badlogic.gdx.utils.Array<E>(size)
    forEach { array.add(it) }
    return array
}

fun VisTable.scrollable() = VisScrollPane(this)