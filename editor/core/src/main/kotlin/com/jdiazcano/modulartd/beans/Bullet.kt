package com.jdiazcano.modulartd.beans

/**
 * A bullet. A bullet is only defined by a resource and the bullet speed! Name for now is not that useful
 */
class Bullet(
        override var resource: Resource,
        var bulletSpeed: Int = 100,
        override var name: String = "",
        override var rotationAngle: Float = 0F,
        override var animationTimer: Float = 0.2F
) : MapObject