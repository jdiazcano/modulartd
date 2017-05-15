package com.plugintest.bestplugin

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.jdiazcano.modulartd.plugins.ui.PreferencesTable
import com.kotcrab.vis.ui.widget.VisTextButton


class BestPreferences : PreferencesTable("Best") {

    private val properties = mutableMapOf<String, String>()

    init {
        val label = VisTextButton("Test")
        label.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                print("Testtttt")
            }
        })
        add(label).expand().left()
    }

    override fun serialize(): Map<String, String> {
        return properties
    }

    override fun deserialize(properties: Map<String, String>) {
        this.properties.clear()
        this.properties.putAll(properties)
    }

}