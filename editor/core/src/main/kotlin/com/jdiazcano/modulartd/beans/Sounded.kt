package com.jdiazcano.modulartd.beans

/**
 * Determines if a MapObject can have sounds or not
 */
interface Sounded<T> {
    val sounds: MutableMap<T, Resource>
}