package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jdiazcano.modulartd.ActionManager
import com.jdiazcano.modulartd.RegisteredActionListener
import com.jdiazcano.modulartd.plugins.actions.Action
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.MenuBar
import com.kotcrab.vis.ui.widget.PopupMenu

class MainMenu() : MenuBar() {
    private val menuItems: MutableMap<Action, ActionedMenuItem> = mutableMapOf()
    private val menus = arrayOf(
            Menu("File"),
            Menu("Edit"),
            Menu("Help")
    )
    init {
        menus.forEach { addMenu(it) }
        ActionManager.addListener(object : RegisteredActionListener {
            override fun process(action: Action, parentAction: Action?) {
                createMenu(action)
            }
        })
    }

    fun createMenu(action: Action) {
        if (action.parentName.startsWith("MM")) {
            val menu = menus[action.parentName.substring(2).toInt()]
            val item = ActionedMenuItem(Image(), action)
            menu.addItem(item)
        } else {
            val parentAction = ActionManager.findAction(action.parentName)
            if (parentAction != null) {
                val parentMenu = findMenuForAction(parentAction)!!
                if (parentMenu.subMenu == null) {
                    parentMenu.subMenu = PopupMenu(parentAction.description)
                }

                val item = ActionedMenuItem(Image(), action)
                parentMenu.subMenu.addItem(item)
                menuItems[action] = item
            }
        }
    }

    fun findMenuForAction(action: Action) = menuItems[action]
}
