package com.jdiazcano.modulartd.beans

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.ResourceManager
import com.jdiazcano.modulartd.injections.kodein

/**
 * Base class for all objects like Turrets, Units, Tiles, whatever.
 *
 * This class will contain:
 * - The name of the object (this shall be unique?)
 * - The resource that will be painted in the game
 * - The rotation angle of the resource that will be painted
 */
open class MapObject(
        var name: String = "Empty",
        var resource: Resource = Resource(),
        var rotationAngle: Float = 0F,
        var script: String = "",
        var animationTimer: Float = 0.2F
) {
    @Transient var nextSprite = 0F
    @Transient var spriteIndex = 0
    @Transient var animation: Boolean = false
    @Transient var changingResource: Boolean = false
    @Transient var textures: MutableList<TextureRegion> = arrayListOf()
    @Transient var sprite: Sprite? = null

    init {

    }

    fun update(delta: Float) {
        if (animation && !changingResource) {
            if (nextSprite > animationTimer) {
                spriteIndex += 1
                spriteIndex %= textures.size
                nextSprite = 0F
            } else {
                nextSprite += delta
            }
        }
    }
}

class MapObjectRenderer {
    private val assetManager: ResourceManager = kodein.instance()
    private val map: Map = kodein.instance()

    fun render(batch: Batch, parentAlpha: Float, x: Float, y: Float, obj: MapObject) {
        if (!obj.changingResource && map.resources.indexOf(obj.resource) >= 0) {
            val color = batch.color
            batch.setColor(color.r, color.g, color.b, color.a * parentAlpha)
            if (obj.animation && obj.textures.isNotEmpty()) {
                batch.draw(obj.textures[obj.spriteIndex],
                        x, y,
                        map.tileWidth / 2, map.tileHeight / 2,
                        map.tileWidth, map.tileHeight,
                        1F, 1F,
                        obj.rotationAngle)
            } else if (obj.sprite != null && assetManager.isLoaded(obj.resource.file.path())) {
                // TODO handle the path relative to the current folder that our project is on right now
                val sprite = obj.sprite!! //TODO jeez I don't like this hack!!
                sprite.rotation = obj.rotationAngle
                sprite.setSize(map.tileWidth, map.tileHeight)
                sprite.setPosition(x, y)
                sprite.setOriginCenter()
                sprite.draw(batch, parentAlpha)
            } else {
                // If we are not animation AND we are not a sprite, we will update our regions, this is
                // because we have serialized and deserialized so the object has not called refreshSprite!!!
                // TODO I don't even remember why I wrote this :)
                // refreshSprite()
            }
        }
    }
}