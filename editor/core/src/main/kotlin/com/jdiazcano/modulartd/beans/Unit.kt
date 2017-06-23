package com.jdiazcano.modulartd.beans

import com.jdiazcano.modulartd.ResourceManager

data class Unit(
        override var name: String,
        override var resource: Resource,
        override var rotationAngle: Float = 0F,
        override var script: String = "",
        override var animationTimer: Float = 0.2F,
        var movementSpeed: Float = 1F,
        var hitPoints: Float = 1F,
        var armor: Float = 0F,
        var invisible: Boolean = false,
        var air: Boolean = false,
        var antiStun: Boolean = false,
        var antiSlow: Boolean = false,
        var livesTaken: Int = 1,
        var hpRegenPerSecond: Float = 0f,
        var drop: MutableMap<Coin, Int> = hashMapOf(),
        override var sounds: MutableMap<UnitEvent, Resource> =
                            UnitEvent.values.associateBy({it}, {ResourceManager.NO_SOUND}).toMutableMap()
): MapObject, Scriptable, Sounded<UnitEvent>

/**
 * Events that can occur to an unit. This will go later to the scripting language because each one of these
 * enum items will map to a method that will be executed when the action happens. But if someone doesn't want
 * to go into that mess, (s)he can just play a sound.
 */
enum class UnitEvent {
    /**
     * Fired when the unit spawns
     */
    ON_SPAWN,
    /**
     * Fired when the unit dies
     */
    ON_DEATH,
    /**
     * Fired when an unit enters a new region.
     */
    ON_REGION,
    /**
     * Fired when the unit reaches the last region.
     */
    ON_FINAL,
    /**
     * Fired when this unit takes damage
     */
    ON_DAMAGE,
    ;

    companion object {
        val values = values()
    }
}