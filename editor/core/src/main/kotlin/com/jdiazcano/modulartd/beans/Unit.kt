package com.jdiazcano.modulartd.beans

class Unit(
        name: String,
        resource: Resource,
        rotationAngle: Float = 0F,
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
): MapObject(name, resource, rotationAngle) {

    fun update(unit: Unit) {
        name = unit.name
        resource = unit.resource
        rotationAngle = unit.rotationAngle
        movementSpeed = unit.movementSpeed
        hitPoints = unit.hitPoints
        armor = unit.armor
        invisible = unit.invisible
        air = unit.air
        antiStun = unit.antiStun
        antiSlow = unit.antiSlow
        livesTaken = unit.livesTaken
        hpRegenPerSecond = unit.hpRegenPerSecond
        drop = unit.drop
        sounds = unit.sounds
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Unit

        if (!super.equals(other)) return false
        if (movementSpeed != other.movementSpeed) return false
        if (hitPoints != other.hitPoints) return false
        if (armor != other.armor) return false
        if (invisible != other.invisible) return false
        if (air != other.air) return false
        if (antiStun != other.antiStun) return false
        if (antiSlow != other.antiSlow) return false
        if (livesTaken != other.livesTaken) return false
        if (hpRegenPerSecond != other.hpRegenPerSecond) return false
        if (drop != other.drop) return false
        if (sounds != other.sounds) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + movementSpeed.hashCode()
        result = 31 * result + hitPoints.hashCode()
        result = 31 * result + armor.hashCode()
        result = 31 * result + invisible.hashCode()
        result = 31 * result + air.hashCode()
        result = 31 * result + antiStun.hashCode()
        result = 31 * result + antiSlow.hashCode()
        result = 31 * result + livesTaken
        result = 31 * result + hpRegenPerSecond.hashCode()
        result = 31 * result + drop.hashCode()
        result = 31 * result + sounds.hashCode()
        return result
    }
}


enum class UnitSoundEvent {
    ON_SPAWN,
    ON_KILL,
    ON_REGION,
    ON_FINAL
}