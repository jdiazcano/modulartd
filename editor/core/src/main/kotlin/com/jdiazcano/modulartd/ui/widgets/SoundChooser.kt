package com.jdiazcano.modulartd.ui.widgets

import com.badlogic.gdx.audio.Sound
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.ResourceManager
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.beans.Sounded
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.utils.*
import com.kotcrab.vis.ui.widget.VisImageButton
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable

/**
 * The SoundChooser will be the class (table) that will be inserted in the Tab and will have a list of events (which
 * will be defined in enums). Each event will have a combobox next to it with all the resources (only sound resources)
 * available to be selected and a button to play the sound.
 */
class SoundChooser<T : Enum<*>>: VisTable(true) {

    private lateinit var item: Sounded<T>

    private fun reloadTable() {
        clearChildren()

        add(translate("Event sounds")).expandX().left().row()

        item.sounds.forEach { event, resource ->
            add(SoundChooserRow(event, resource) { selectedResource ->
                item.sounds[event] = selectedResource
            }).expandX().fillX().row()
        }

        invalidate()
    }

    fun update(item: Sounded<T>) {
        this.item = item
        reloadTable()
    }
}

class SoundChooserRow<out T : Enum<*>>(
        val event: T,
        val resource: Resource,
        val listener: (Resource) -> Unit
): VisTable() {
    private val eventLabel = VisLabel(translate(event.name))
    private val play = VisImageButton(icon("volume").asDrawable())
    private val combo = SoundSelectBox()

    init {
        combo.selected = resource.file.nameWithoutExtension()

        add(eventLabel).padRight(3F)
        add(combo).expandX().fillX()
        add(play).padLeft(3F)

        createListeners()
    }

    private fun createListeners() {
        combo.sneakyChange {
            listener(combo.selectedResource())
        }

        play.sneakyChange {
            val sound = kodein.instance<ResourceManager>().get(combo.selectedResource().file.path(), Sound::class.java)
            sound.play(1.0F)
        }
    }
}

class SoundSelectBox : VisSelectBox<String>() {

    private lateinit var itemResources: Iterable<Resource>

    init {
        reloadSounds()

        Bus.register<Resource>(BusTopic.CREATED) {
            if (it.resourceType == ResourceType.SOUND) {
                reloadSounds()
            }
        }
    }

    private fun reloadSounds() {
        itemResources = kodein.instance<ResourceManager>()
                .getResourcesByType(ResourceType.SOUND)
        items = itemResources
                .map { it.file.nameWithoutExtension() }
                .asGdxArray()
    }

    fun selectedResource(): Resource {
        return itemResources.filter { it.file.nameWithoutExtension() == selected }.first()
    }

}


