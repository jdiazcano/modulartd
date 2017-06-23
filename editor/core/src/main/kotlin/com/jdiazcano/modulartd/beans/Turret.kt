package com.jdiazcano.modulartd.beans

import com.jdiazcano.modulartd.ResourceManager

/**
 * Class that describes a Turret
 */
data class Turret(
        override var name: String,
        override var resource: Resource,
        var attackSpeed: Float = 1F,
        var damage: Float = 50F,
        var bullet: Bullet = Bullet(Resource(), 100),
        override var script: String = "",
        override var rotationAngle: Float = 0F,
        override var animationTimer: Float = 0.2F,
        var viewInvisibles: Boolean = false,
        var costMap: MutableMap<Coin, Int> = hashMapOf(),
        var superTurrets: MutableList<Turret> = arrayListOf(),
        var range: Float = 100F,
        var splashRadius: Float = 0F,
        var numberOfTargets: Int = 1,
        var attacksAir: Boolean = false,
        var canSlow: Boolean = false,
        var slowChance: Float = 0F,
        var slowPercent: Float = 0F,
        var slowDuration: Float = 0F,
        var canCrit: Boolean = false,
        var critChance: Float = 0F,
        var critMultiplier: Float = 1F,
        var canStun: Boolean = false,
        var stunChance: Float = 0F,
        var stunDuration: Float = 0F,
        override var sounds: MutableMap<TurretEvent, Resource> =
                        TurretEvent.values.associateBy({it}, { ResourceManager.NO_SOUND}).toMutableMap()
) : MapObject, Scriptable, Sounded<TurretEvent>

/**
 * Events that can occur to an turret. This will go later to the scripting language because each one of these
 * enum items will map to a method that will be executed when the action happens. But if someone doesn't want
 * to go into that mess, (s)he can just play a sound.
 */
enum class TurretEvent {
    /**
     * Fired when the turret is built
     */
    ON_BUILT,
    /**
     * Fired when the turret shoots
     */
    ON_SHOOT,
    /**
     * Fired when the turret is sold
     */
    ON_SELL,
    /**
     * Fired when the turret kills an unit
     */
    ON_KILL,
    ;

    companion object {
        val values = values()
    }
}