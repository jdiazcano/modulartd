package com.jdiazcano.modulartd.plugins

class ExitPlugin : AbstractPlugin() {

    override fun getName(): String = "Best plugin ever"

    override fun getVersion(): Int = 1

    override fun getMinimumCompatibleVersion(): Int = 1

    override fun onLoad() {
        super.onLoad()
        print("This is the exit plugin!")
    }
}
