package com.jdiazcano.modulartd.ui.widgets

import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.ui.AnimatedActor
import com.kotcrab.vis.ui.widget.VisTable

class ResourceView(resource: Resource) : VisTable() {

    private val image: AnimatedActor = AnimatedActor(resource)

    init {
        add(image).size(50F).center().row()
        add(resource.file.nameWithoutExtension()).center()
        pad(10f)
        pack()
    }
}
