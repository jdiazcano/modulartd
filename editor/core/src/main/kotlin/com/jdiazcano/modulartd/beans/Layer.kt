package com.jdiazcano.modulartd.beans

/**
 * Layer: this defines a layer in the map. The map can be composed by multiple layers, more or less like how they work
 * in any image editing program. The thing is that you will be able to have different layers one with the background
 * (animated background!) and then some forest in front of it (before building any turrets over it!)
 *
 * Created by javierdiaz on 16/11/2016.
 */
class Layer(
        name: String
) {
    var visible: Boolean = true
    lateinit var tiles: Array<Array<Tile>>
}