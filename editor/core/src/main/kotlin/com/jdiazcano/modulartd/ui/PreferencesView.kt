package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTree

class PreferencesView : VisTable() {
    private val preferencesTree = VisTree()
    private val panel = VisTable()

    init {
        add(preferencesTree).apply {
            expandY
            width(Value.percentHeight(0.2F))
            fill()
        }
        add(ScrollPane(panel)).apply {
            expand()
            fill()
        }
    }
}