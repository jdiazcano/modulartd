package com.jdiazcano.modulartd.ui.widgets

import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.ResourceManager
import com.jdiazcano.modulartd.beans.MapObject
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.utils.clickListener
import com.kotcrab.vis.ui.layout.GridGroup
import com.kotcrab.vis.ui.widget.VisDialog
import com.kotcrab.vis.ui.widget.VisScrollPane

class ResourcePicker(title: String) : VisDialog(title) {

    var listeners: MutableList<(Resource) -> Unit> = arrayListOf()

    var resourceGrid: GridGroup = GridGroup(60f, 7f)

    init {
        val sp = VisScrollPane(resourceGrid)
        sp.setScrollingDisabled(true, false)
        contentTable.add(sp).expand().fill()
        addCloseButton()
        closeOnEscape()
        isResizable = true
        setSize(500F, 400F)
        centerWindow()
    }

    fun pickResource(type: ResourceType) {
        buildTable(type)
        Bus.post(fadeIn(), BusTopic.NEW_DIALOG)
        toFront()
    }

    fun buildTable(type: ResourceType) {
        resourceGrid.clearChildren()

        val resources = kodein.instance<ResourceManager>().getResourcesByType(type)

        resources.forEach { resource ->
            val view = ResourceView(MapObject(resource = resource))
            view.clickListener { _, _, _ ->
                listeners.forEach { it(resource) }
                fadeOut()
            }

            resourceGrid.addActor(view)
        }

        invalidate()
    }

    fun addPickListener(listener: (Resource) -> Unit) {
        this.listeners.add(listener)
    }

}

/**
 * Creates a simple dialog for a ResourcePicker with a custom listener so you don't have to actually know all the
 * insiders of the ResourcePicker
 */
fun pickResource(title: String, type: ResourceType, listener: (Resource) -> Unit) {
    val picker = ResourcePicker(title)
    picker.addPickListener(listener)
    picker.pickResource(type)
}