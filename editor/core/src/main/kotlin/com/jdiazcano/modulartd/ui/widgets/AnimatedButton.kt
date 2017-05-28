package com.jdiazcano.modulartd.ui.widgets

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.jdiazcano.modulartd.beans.MapObject
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.ui.AnimatedActor
import com.kotcrab.vis.ui.Focusable
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisImageButton

class AnimatedButton(private val actor: AnimatedActor) : Button(), Focusable {
    private lateinit var style: VisImageButton.VisImageButtonStyle

    private var drawBorder: Boolean = false

    init {
        setStyle(VisImageButton.VisImageButtonStyle(VisUI.getSkin().get(VisImageButton.VisImageButtonStyle::class.java)))

        add(actor).size(50F)
        setSize(prefWidth, prefHeight)

        addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                //TODO fix this if (!isDisabled) FocusManager.getFocus(this@AnimatedButton)
                return false
            }
        })
    }

    override fun setRotation(degrees: Float) {
        actor.rotation = degrees
    }

    override fun getRotation(): Float {
        return actor.rotation
    }

    var mapObject: MapObject
        get() = actor.mapObject
        set(obj) {
            actor.mapObject = obj
        }

    var resource: Resource
        get() = actor.mapObject.resource
        set(r) {
            actor.swapResource(r)
        }

    override fun setStyle(style: Button.ButtonStyle) {
        if (style !is VisImageButton.VisImageButtonStyle) throw IllegalArgumentException("style must be an ImageButtonStyle.")
        super.setStyle(style)
        this.style = style
    }

    override fun getStyle(): VisImageButton.VisImageButtonStyle {
        return style
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (drawBorder) style.focusBorder.draw(batch, x, y, width, height)
    }

    override fun setDisabled(disabled: Boolean) {
        super.setDisabled(disabled)
        // TODO Fix this if (disabled) FocusManager.getFocus()
    }

    override fun focusLost() {
        drawBorder = false
    }

    override fun focusGained() {
        drawBorder = true
    }
}