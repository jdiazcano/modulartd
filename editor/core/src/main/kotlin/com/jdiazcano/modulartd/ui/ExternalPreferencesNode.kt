package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Tree
import com.badlogic.gdx.scenes.scene2d.utils.Selection
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.plugins.ui.PreferencesTable
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSplitPane
import com.kotcrab.vis.ui.widget.VisTable

// Made to be instantiated only once :p
class ExternalPreferencesNode(actor: Actor) : Tree.Node(actor) {

    private val nodeTables = mutableMapOf<Tree.Node, VisTable>()
    init {
        Bus.register<PreferencesTable>(BusTopic.PREFERENCES_REGISTERED) { table ->
            val label = VisLabel(table.name)
            val node = Tree.Node(label)
            nodeTables[node] = table
            add(node)
        }
    }

    fun selectionChanged(selection: Selection<Tree.Node>, panel: VisSplitPane) {
        panel.setSecondWidget(nodeTables[selection.lastSelected])
    }

}