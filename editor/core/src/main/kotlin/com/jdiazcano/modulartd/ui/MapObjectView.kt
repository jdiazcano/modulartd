package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.jdiazcano.modulartd.beans.MapObject
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.utils.clickListener
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.MenuItem
import com.kotcrab.vis.ui.widget.PopupMenu
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable

class MapObjectView(item: MapObject, private val vertical: Boolean = false) : VisTable() {

    var image = AnimatedActor(item.resource)
    var labelName = VisLabel(item.name)
    val rightClickMenu = PopupMenu()
    val deleteItem = MenuItem(translate("delete"))

    init {
        add(image).size(50F, 50F)
        if (vertical) {
            row()
        }
        add(labelName).expand().fill()

        rightClickMenu.addItem(deleteItem)
        deleteItem.clickListener { _, _, _ ->
            Bus.post(item, BusTopic.DELETED)
        }
        addListener(object : InputListener() {

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                event?.let {
                    if (it.button == Input.Buttons.RIGHT) {
                        Bus.post(PopupMenuLocation(rightClickMenu, it.stageX, it.stageY), BusTopic.POPUP_MENU)
                    }
                }
            }
        })
    }

    override fun setRotation(degrees: Float) {
        image.rotation = degrees
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

data class PopupMenuLocation(val menu: PopupMenu, val x: Float, val y: Float)