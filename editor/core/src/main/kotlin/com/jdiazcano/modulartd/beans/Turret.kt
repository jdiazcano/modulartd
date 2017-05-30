package com.jdiazcano.modulartd.beans

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
        var soundMap: MutableMap<TurretSound, Resource> = hashMapOf()
) : MapObject, Scriptable


enum class TurretSound {
    ON_BUILT,
    ON_SHOOT,
    ON_SELL,
    ON_KILL,
}