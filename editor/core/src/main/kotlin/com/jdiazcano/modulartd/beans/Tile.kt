package com.jdiazcano.modulartd.beans

/**
 * Tile class that defines the piece of the terrain that will be constructing the grid.
 *
 * Created by javierdiaz on 16/11/2016.
 */
data class Tile(
        override var name: String,
        override var resource: Resource,
        var buildable: Boolean = true,
        override var script: String = "",
        override var rotationAngle: Float = 0F,
        override var animationTimer: Float = 0.2F
) : MapObject, Scriptable