package com.jdiazcano.modulartd.plugins

interface Plugin {
    fun getName() : String

    fun getVersion() : Int

    fun getMinimumCompatibleVersion() : Int

    /**
     * This method will be called when a plugin is done, so here you can register your plugin into the bus to receive
     * events or add new items to menus
     */
    fun onLoad()

    fun onUnload()
}