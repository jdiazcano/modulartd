package com.jdiazcano.modulartd.beans

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureAtlas

/**
 * A resource defines a file and the type of that file that will be used inside the MapObjects to be drawn in the game
 */
class Resource(
        var file: FileHandle = Gdx.files.absolute(""),
        var resourceType: ResourceType = ResourceType.EMPTY
) {

    fun assetType(): Class<*> {
        return if ("wav|ogg|mp3".contains(file.extension())) {
            Sound::class.java
        } else if ("atlas".contains(file.extension())) {
            TextureAtlas::class.java
        } else {
            Sprite::class.java
        }
    }

}