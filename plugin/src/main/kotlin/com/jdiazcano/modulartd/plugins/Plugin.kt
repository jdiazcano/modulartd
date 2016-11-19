package com.jdiazcano.modulartd.plugins

interface Plugin {

    /**
     * Name of your plugin
     */
    fun getName(): String

    /**
     * Plugin version, this will be mostly used when showing the list of loaded plugins
     */
    fun getVersion(): Int

    /**
     * A plugin might not be compatible from the first version to the last due to api changes so this will declare which
     * is the minimum version that your plugin works with
     */
    fun getMinimumCompatibleVersion(): Int

    /**
     * Prefix that will be used to distinguish your plugin. Same plugin name or action names can clash but with the
     * prefix it should not be any clash.
     */
    fun getPrefix(): String

    /**
     * This method will be called when a plugin is loaded, so here you can register your plugin into the bus to receive
     * events or add new items to menus
     */
    fun onLoad()

    /**
     * This method will be called when a plugin being unloaded from the list of plugins so here you can write the shut
     * down mechanism for your plugin! (Freeing memory etc)
     */
    fun onUnload()
}