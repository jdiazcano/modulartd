package com.jdiazcano.modulartd.ui.widgets

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.jdiazcano.modulartd.beans.Layer
import com.jdiazcano.modulartd.utils.asDrawable
import com.jdiazcano.modulartd.utils.icon
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.VisImageButton
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable

class LayerView(
        var layer: Layer,
        vertical: Boolean = false
): VisTable() {

    val check: VisImageButton
    val name  = VisLabel(translate("name"))

    init {
        val style = VisImageButton.VisImageButtonStyle()
        style.checked = icon("visibility").asDrawable()
        style.up = icon("visibility_off").asDrawable()
        check = VisImageButton(style)
        check.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                layer.visible = check.isChecked
            }
        })

        name.setText(layer.name)

        add(check).size(24F).pad(10F)
        check.isChecked = layer.visible
        if (vertical) {
            row()
        }
        add(name).expand().fill()
    }

}