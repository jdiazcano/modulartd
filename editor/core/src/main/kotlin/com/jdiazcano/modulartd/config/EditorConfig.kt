package com.jdiazcano.modulartd.config;

interface EditorConfig {
    /**
     * Folder that will contain all the plugins
     */
    fun pluginsFolder(): String

    /**
     * Filename that will contain the plugin files that will be loaded
     */
    fun pluginsConfigFile() : String

    /**
     * Default grid width
     */
    fun gridWidth(): Int

    /**
     * The maximum grid width, a map will not be loaded over this width (and the GUI will not permit it) as performance
     * problems may appear if you have a too big map
     */
    fun maxGridWidth(): Int

    /**
     * Maximum grid height
     */
    fun maxGridHeight(): Int

    /**
     * Default grid height
     */
    fun gridHeight(): Int

    /**
     * Default tile width
     */
    fun tileWidth(): Int

    /**
     * Default tile height
     */
    fun tileHeight(): Int

    /**
     * The time that will have to pass between levels
     *
     * TODO Think if this would be better to have like a sequence of commands instead of having a fixed time
     */
    fun timeBetweenLevels(): Float

    /**
     * The interest ratio, this means the % of gold (0-100%) that will be added to the player at the end of each round
     */
    fun interestRatio(): Float

    /**
     * This is how much gold (0-100%) will be added when a player sells an already used turret.
     *
     * An used turret means that it has shot at least once
     */
    fun turretSellProfit(): Float

    /**
     * Number of units needed for triggering the end condition
     */
    fun unitCount(): Int
}
