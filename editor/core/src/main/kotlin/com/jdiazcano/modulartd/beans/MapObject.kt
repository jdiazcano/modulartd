package com.jdiazcano.modulartd.beans

/**
 * Base class for all objects like Turrets, Units, Tiles, whatever.
 *
 * This class will contain:
 * - The name of the object (this shall be unique?)
 * - The resource that will be painted in the game
 * - The rotation angle of the resource that will be painted
 */
interface MapObject {
    companion object {
        val EMPTY = object : MapObject {
            override var name = ""
            override var resource = Resource()
            override var rotationAngle = 0F
            override var animationTimer = 0.2F

        }
    }

    var name: String
    var resource: Resource
    var rotationAngle: Float
    var animationTimer: Float
}