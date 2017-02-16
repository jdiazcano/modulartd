package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.jdiazcano.modulartd.ui.ExternalPreferencesNode
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
    fun exit() = PreferencesAction()
}

class PreferencesAction : Action("edit.preferences", "Preferences", ShortCut(Input.Keys.COMMA, Modifiers(alt = true))) {
    private val preferences = PreferencesWindow()

    override fun perform(stage: StageWrapper) {
        stage.addWindow(preferences)
    }
}

class PreferencesWindow : VisWindow("Preferences") {
    private val tree: VisTree = VisTree()
    private val mainTable = VisTable()

    private val externalNode = ExternalPreferencesNode(VisLabel("Other"))

    init {
        debugAll()
        tree.add(externalNode)
        tree.setFillParent(true)
        mainTable.setFillParent(true)

        add(VisSplitPane(tree, mainTable, false)).expand().fill()

        isResizable = true
        centerWindow()
        closeOnEscape()
        addCloseButton()
    }
}
