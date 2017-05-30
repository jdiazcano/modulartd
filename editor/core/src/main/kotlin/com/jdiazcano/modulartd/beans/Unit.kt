package com.jdiazcano.modulartd.beans

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
        var sounds: MutableMap<UnitSoundEvent, Resource> = hashMapOf()
): MapObject, Scriptable

enum class UnitSoundEvent {
    ON_SPAWN,
    ON_KILL,
    ON_REGION,
    ON_FINAL
}