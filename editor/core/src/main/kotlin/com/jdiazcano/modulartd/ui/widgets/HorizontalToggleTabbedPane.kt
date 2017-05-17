package com.jdiazcano.modulartd.ui.widgets

import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTextButton.VisTextButtonStyle
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane

class HorizontalToggleTabbedPane: TabbedPane(getStyle()) {
    init {

        isAllowTabDeselect = true

    }

    companion object {
        fun getStyle(): TabbedPaneStyle {
            val tabStyle = TabbedPane.TabbedPaneStyle(VisUI.getSkin().get(TabbedPane.TabbedPaneStyle::class.java))
            tabStyle.buttonStyle = VisTextButtonStyle(VisUI.getSkin().get("toggle", VisTextButtonStyle::class.java))
            tabStyle.buttonStyle.font = VisUI.getSkin().getFont("small-font")
            tabStyle.separatorBar = null
            tabStyle.draggable = false
            return tabStyle
        }
    }
}