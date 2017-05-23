package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.jdiazcano.modulartd.beans.MapObject
import com.jdiazcano.modulartd.beans.ResourceType

class AnimatedActor(private val mapObject: MapObject, private val spriteTimer: Float = 0.2F): Actor() {
    private var spriteIndex = 0
    private var nextSprite = 0.0F
    private var isSprite = false
    private var changingResource = false

    val position = Vector2()
    val center = Vector2()
    val bounds = Rectangle()

    private val images = arrayListOf<TextureAtlas.AtlasRegion>()

    init {
        loadResource()
    }

    private fun loadResource() {
        mapObject
        if (mapObject.resource.resourceType != ResourceType.EMPTY) {

        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
    }

    override fun act(delta: Float) {
        if (isSprite && !changingResource) {
            if (nextSprite > spriteTimer) {
                spriteIndex += 1
                spriteIndex %= images.size
                nextSprite = 0f
            } else {
                nextSprite += delta
            }
        }
    }
}