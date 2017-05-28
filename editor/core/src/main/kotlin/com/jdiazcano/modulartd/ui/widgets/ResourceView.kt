package com.jdiazcano.modulartd.ui.widgets

import com.jdiazcano.modulartd.beans.MapObject
import com.jdiazcano.modulartd.ui.AnimatedActor
import com.kotcrab.vis.ui.widget.VisTable

class ResourceView(item: MapObject = MapObject()) : VisTable() {

    val resource = item.resource
    private val image: AnimatedActor = AnimatedActor(item)

    init {
        add(image).size(50f).center().row()
        add(resource.file.nameWithoutExtension()).center()
        pad(10f)
        pack()
    }
}
