package com.jdiazcano.modulartd.beans

/**
 * Tile class that defines the piece of the terrain that will be constructing the grid.
 *
 * Created by javierdiaz on 16/11/2016.
 */
data class Tile(
        override var name: String,
        override var resource: Resource,
        override var script: String,
        override var rotationAngle: Float,
        override var animationTimer: Float
) : MapObject, Scriptable