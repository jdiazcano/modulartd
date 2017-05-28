package com.jdiazcano.modulartd.beans

/**
 * Type of the resource, this type defines a folder which the assets that are created inside, will be picked up
 * automatically and import and will be able to be used within the game.
 */
enum class ResourceType(val folder: String) {
    EMPTY("nothing"),
    SOUND("sounds"),
    IMAGE("images"),
}