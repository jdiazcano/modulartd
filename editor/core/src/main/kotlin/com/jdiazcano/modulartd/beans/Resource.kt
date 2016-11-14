package com.jdiazcano.modulartd.beans

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle

/**
 * A resource defines a file and the type of that file that will be used inside the MapObjects to be drawn in the game
 */
class Resource(
        var file: FileHandle = Gdx.files.absolute(""),
        var resourceType: ResourceType = ResourceType.EMPTY) {

}