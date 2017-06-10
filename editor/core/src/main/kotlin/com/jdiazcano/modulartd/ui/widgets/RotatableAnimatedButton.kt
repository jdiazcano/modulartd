package com.jdiazcano.modulartd.ui.widgets

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.utils.changeListener
import com.jdiazcano.modulartd.utils.icon
import com.kotcrab.vis.ui.widget.VisImageButton
import com.kotcrab.vis.ui.widget.VisTable

class RotatableAnimatedButton(private var actor: AnimatedButton) : VisTable() {
    private val rotateLeft = VisImageButton(SpriteDrawable(Sprite(icon("rotate-left"))))
    private val rotateRight = VisImageButton(SpriteDrawable(Sprite(icon("rotate-right"))))

    init {

        rotateLeft.changeListener { _, _ ->
            increaseRotation(90F)
            Bus.post(actor.mapObject, BusTopic.UPDATED)
        }

        rotateRight.changeListener { _, _ ->
            increaseRotation(-90F)
            Bus.post(actor.mapObject, BusTopic.UPDATED)
        }

        val rotateTable = VisTable()

        rotateTable.add(rotateLeft).size(24F).padBottom(2F).row()
        rotateTable.add(rotateRight).size(24F).row()

        add(actor).padRight(3F)
        add(rotateTable)

    }

    override fun getRotation(): Float {
        return actor.rotation
    }

    override fun setRotation(degrees: Float) {
        actor.rotation = degrees
    }

    private fun increaseRotation(degrees: Float) {
        actor.rotation = actor.rotation + degrees
    }

    fun setActor(actor: AnimatedButton) {
        this.actor = actor
    }

}

// Shit! The AnimatedButton only has the resource and it uses the rotation of the actor to rotate instead of using the rotation of the MapObject :(
// Now I have to make it use a MapObject and not only a resource and that's sad :(

fun AnimatedButton.rotatable() = RotatableAnimatedButton(this)