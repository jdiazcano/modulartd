package com.jdiazcano.modulartd.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab

open abstract class BaseTab(val title: String): Tab(true, false) {
    internal val content = VisTable()

    init {
        content.add(title)
    }

    override fun getContentTable(): Table {
        return content
    }

    override fun getTabTitle() = title
}