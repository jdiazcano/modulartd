package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.jdiazcano.modulartd.ui.ExternalPreferencesNode
import com.jdiazcano.modulartd.utils.changeListener
import com.kotcrab.vis.ui.widget.*

/**
 * This plugin show the Preferences window.
 */
class PreferencesPlugin : Plugin {
    override fun getName() = "Preferences plugin"

    override fun getVersion() = 1

    override fun getMinimumCompatibleVersion() = 1

    override fun getPrefix() = "preferences"

    override fun onLoad() {}

    override fun onUnload() {}

    @RegisterAction(Menus.EDIT)
    fun preferences() = PreferencesAction()
}

class PreferencesAction : Action("edit.preferences", "Preferences", ShortCut(Input.Keys.COMMA, Modifiers(alt = true))) {
    private val preferences = PreferencesWindow()

    override fun perform(stage: StageWrapper) {
        stage.addWindow(preferences)
        preferences.width = preferences.stage.width/2
        preferences.height = preferences.stage.height/2
        preferences.centerWindow()
    }
}

class PreferencesWindow : VisWindow("Preferences") {
    private val tree = VisTree()
    private val mainTable = VisTable()

    private val externalNode = ExternalPreferencesNode(VisLabel("Other"))

    init {
        isResizable = true
        closeOnEscape()
        addCloseButton()

        val panel = VisSplitPane(tree, mainTable, false)
        tree.add(externalNode)
        tree.changeListener { _, actor ->
            externalNode.selectionChanged((actor as VisTree).selection, panel)
        }

        panel.setDebug(true, true)
        add(panel).expand().fill()
        //mainTable.setFillParent(true)
    }
}
