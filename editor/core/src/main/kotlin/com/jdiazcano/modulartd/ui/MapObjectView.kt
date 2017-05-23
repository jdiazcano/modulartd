package com.jdiazcano.modulartd.ui

import com.jdiazcano.modulartd.beans.MapObject
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable

class MapObjectView(unit: MapObject, private val vertical: Boolean = false) : VisTable() {

    var image: AnimatedActor? = null
    val labelName: VisLabel

    init {
        image = AnimatedActor(MapObject())
        labelName = VisLabel(unit.name)

        add(image).size(50f, 50f)
        if (vertical) {
            row()
        }
        add(labelName).expand().fill()
    }

    override fun setRotation(degrees: Float) {
        image?.rotation = degrees
    }

    fun updateImage(image: AnimatedActor) {
        this.image = image
        clearChildren()
        add(image).size(50f)
        if (vertical) {
            row()
        }
        add(labelName).expand().fill()
    }

}