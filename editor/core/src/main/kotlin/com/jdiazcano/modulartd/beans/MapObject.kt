package com.jdiazcano.modulartd.beans

/**
 * Base class for all objects like Turrets, Units, Tiles, whatever.
 *
 * This class will contain:
 * - The name of the object (this shall be unique?)
 * - The resource that will be painted in the game
 * - The rotation angle of the resource that will be painted
 */
abstract class MapObject(
        var name: String = "Empty",
        var resource: Resource = Resource(),
        var rotationAngle: Float = 0F
) {

}

/**
 * Base class for all animated objects
 */
abstract class AnimatedMapObject(
        name: String = "Empty",
        resource: Resource = Resource(),
        rotationAngle: Float,
        var animationTimer: Float
): MapObject(name, resource, rotationAngle)