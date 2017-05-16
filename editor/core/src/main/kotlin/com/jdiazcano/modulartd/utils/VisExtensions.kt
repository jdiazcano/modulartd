package com.jdiazcano.modulartd.utils

import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter

/**
 * Small extensions for the VisUI that allows us using Kotlin features
 */
fun TabbedPane.addListener(listener: (Tab) -> Unit) = addListener(object: TabbedPaneAdapter() {
    override fun switchedTab(tab: Tab?) {
        tab?.let { listener(tab) }
    }
})