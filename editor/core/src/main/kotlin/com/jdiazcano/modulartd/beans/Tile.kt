package com.jdiazcano.modulartd.beans

/**
 * Tile class that defines the piece of the terrain that will be constructing the grid.
 *
 * Created by javierdiaz on 16/11/2016.
 */
class Tile(
        name: String,
        resource: Resource,
        rotationAngle: Float,
        var buildable: Boolean
) : MapObject(name, resource, rotationAngle), Scriptable {
    override var script: String = ""
}