package com.jdiazcano.modulartd.ui.widgets

import com.jdiazcano.modulartd.beans.Layer
import com.jdiazcano.modulartd.utils.asDrawable
import com.jdiazcano.modulartd.utils.changeListener
import com.jdiazcano.modulartd.utils.icon
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.VisImageButton
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable

/**
 * This class is used
 */
class LayerView(
        var layer: Layer,
        vertical: Boolean = false
): VisTable() {

    val check: VisImageButton
    val name  = VisLabel(translate("name"))

    init {
        val style = VisImageButton.VisImageButtonStyle()
        style.checked = icon("visible").asDrawable()
        style.up = icon("visible-off").asDrawable()
        check = VisImageButton(style)
        check.changeListener { _, _ ->
            layer.visible = check.isChecked
        }

        name.setText(layer.name)

        add(check).size(24F).pad(10F)
        check.isChecked = layer.visible
        if (vertical) {
            row()
        }
        add(name).expand().fill()
    }

}