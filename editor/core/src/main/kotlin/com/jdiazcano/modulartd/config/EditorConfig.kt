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
    fun tileWidth(): Float

    /**
     * Default tile height
     */
    fun tileHeight(): Float

    /**
     * Maximum tile width
     */
    fun maxTileWidth(): Float

    /**
     * Maximum tile height
     */
    fun maxTileHeight(): Float

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

    /**
     * The key that will be used for libgdx preferences that will manage the configuration made to the editor.
     */
    fun preferencesKey(): String

    /**
     * The file that will contain the whole map!
     */
    fun gameFileName(): String

    /**
     * The folder that will contain everything related to the map, this will be the folder that denotes if a map has
     * been created in a folder or not!
     */
    fun gameConfigFolder(): String

    /**
     * The base title for the window. This will change overtime for example when opening a map!
     *
     * ModularTD 1.0 (base)
     * C:\Whatever\bestmap - ModularTD 1.0 (when a map has been opened)
     */
    fun baseTitle(): String
}
