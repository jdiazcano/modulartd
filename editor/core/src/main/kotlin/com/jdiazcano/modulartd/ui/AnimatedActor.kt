package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.ResourceManager
import com.jdiazcano.modulartd.beans.MapObject
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.injections.kodein

class AnimatedActor(var mapObject: MapObject = MapObject(), private val spriteTimer: Float = 0.2F): Actor() {
    private var spriteIndex = 0
    private var nextSprite = 0.0F
    private var isSprite = false
    private var changingResource = false

    val position = Vector2()
    val center = Vector2()
    val bounds = Rectangle()

    private val images = arrayListOf<TextureAtlas.AtlasRegion>()
    private var sprite: Sprite? = null

    init {
        loadResource()
    }

    private fun loadResource() {
        val resourceManager = kodein.instance<ResourceManager>()
        if (mapObject.resource.resourceType != ResourceType.EMPTY) {
            if (mapObject.resource.assetType() == TextureAtlas::class.java) {
                images.clear()
                images.addAll(resourceManager.get(mapObject.resource.file.path(), TextureAtlas::class.java).regions)
                isSprite = true
            } else {
                sprite = resourceManager.get(mapObject.resource.file.path(), Sprite::class.java)
            }
        }
    }

    fun swapResource(resource: Resource) {
        changingResource = true

        isSprite = false
        spriteIndex = 0
        mapObject.resource = resource
        loadResource()

        changingResource = false
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        val resourceManager = kodein.instance<ResourceManager>()
        if (!changingResource && mapObject.resource in resourceManager) {
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha)
            if (isSprite) {
                batch.draw(images[spriteIndex], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
            } else if (sprite != null && resourceManager.isLoaded(mapObject.resource.file.path())) {
                sprite?.apply {
                    rotation = this@AnimatedActor.rotation
                    setSize(this@AnimatedActor.width, this@AnimatedActor.height)
                    setPosition(this@AnimatedActor.x, this@AnimatedActor.y)
                    setOriginCenter()
                    draw(batch, parentAlpha)
                }
            } else {
                super.draw(batch, parentAlpha)
            }
        } else {
            super.draw(batch, parentAlpha)
        }
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