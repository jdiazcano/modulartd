package com.jdiazcano.modulartd.keys

/**
 * Modifier class that will define the modifiers for a Key, this class is used inside ShortCut to see which modifiers a
 * key can have
 */
data class Modifiers(
        val alt: Boolean = false,
        val shift: Boolean = false,
        val control: Boolean = false,
        val command: Boolean = false
)