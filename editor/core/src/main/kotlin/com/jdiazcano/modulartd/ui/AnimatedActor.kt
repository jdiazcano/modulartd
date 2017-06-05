package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Actor
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.ResourceManager
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.injections.kodein

class AnimatedActor(var resource: Resource = Resource(), var spriteTimer: Float = 0.2F): Actor() {
    private var spriteIndex = 0
    private var nextSprite = 0.0F
    private var isAnimation = false
    private var changingResource = false

    private val images = arrayListOf<TextureAtlas.AtlasRegion>()
    private var sprite: Sprite? = null

    init {
        loadResource()
    }

    private fun loadResource() {
        val resourceManager = kodein.instance<ResourceManager>()
        if (resource.resourceType != ResourceType.EMPTY) {
            if (resource.assetType() == TextureAtlas::class.java) {
                images.clear()
                images.addAll(resourceManager.get(resource.file.path(), TextureAtlas::class.java).regions)
                isAnimation = true
            } else {
                sprite = resourceManager.get(resource.file.path(), Sprite::class.java)
                isAnimation = false
            }
            setOrigin(width/2, height/2)
        }
    }



    override fun draw(batch: Batch, parentAlpha: Float) {
        val resourceManager = kodein.instance<ResourceManager>()
        if (!changingResource && resource in resourceManager) {
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha)
            if (isAnimation) {
                batch.draw(images[spriteIndex], x, y, originX, originY, width, height, scaleX, scaleY, rotation)
            } else if (sprite != null && resourceManager.isLoaded(resource.file.path())) {
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
        if (isAnimation && !changingResource) {
            if (nextSprite > spriteTimer) {
                spriteIndex += 1
                spriteIndex %= images.size
                nextSprite = 0f
            } else {
                nextSprite += delta
            }
        }
    }

    fun swapResource(resource: Resource) {
        this.resource = resource
        loadResource()
    }

}