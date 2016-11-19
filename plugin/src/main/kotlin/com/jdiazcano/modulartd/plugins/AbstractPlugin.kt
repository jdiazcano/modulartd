package com.jdiazcano.modulartd.plugins

abstract class AbstractPlugin : Plugin {

    override fun onLoad() {
        println("Plugin loaded: ${getName()}")
    }

    override fun onUnload() {
        println("Plugin unloaded: ${getName()}")
    }

}