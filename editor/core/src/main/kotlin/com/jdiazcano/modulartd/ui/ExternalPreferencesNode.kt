package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Tree
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.plugins.ui.PreferencesTable
import com.kotcrab.vis.ui.widget.VisLabel

class ExternalPreferencesNode(actor: Actor) : Tree.Node(actor) {

    init {
        Bus.register(PreferencesTable::class.java, BusTopic.PREFERENCES_REGISTERED) { table ->
            add(Tree.Node(VisLabel(table.name)))
        }

        actor.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("test")
            }
        })
    }
}