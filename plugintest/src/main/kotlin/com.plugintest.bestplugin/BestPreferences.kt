package com.plugintest.bestplugin

import com.jdiazcano.modulartd.plugins.ui.PreferencesTable
import ktx.vis.table


class BestPreferences : PreferencesTable() {

    private val properties = mutableMapOf<String, String>()

    init {
        add(
            table {
                setFillParent(true)
                button {
                    name = "Best button"
                    addListener {
                        println("Clicked!")
                        true
                    }
                }
            }
        )
    }

    override fun serialize(): Map<String, String> {
        return properties
    }

    override fun deserialize(properties: Map<String, String>) {
        this.properties.clear()
        this.properties.putAll(properties)
    }

}