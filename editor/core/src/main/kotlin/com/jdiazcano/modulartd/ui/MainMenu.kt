package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jdiazcano.modulartd.ActionManager
import com.jdiazcano.modulartd.RegisteredActionListener
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.MenuBar
import com.kotcrab.vis.ui.widget.PopupMenu

class MainMenu : MenuBar() {
    private val menuItems: MutableMap<Action, ActionedMenuItem> = mutableMapOf()
    private val menus = mapOf(
            Menus.FILE to Menu("File"),
            Menus.EDIT to Menu("Edit"),
            Menus.GAME to Menu("Game"),
            Menus.HELP to Menu("Help")
    )
    init {
        menus.forEach { addMenu(it.value) }
        ActionManager.addListener(object : RegisteredActionListener {
            override fun process(action: Action, parentId: String) {
                createMenu(action, parentId)
            }
        })
    }

    fun createMenu(action: Action, parentId: String) {
        if (parentId in menus) {
            val menu = menus[parentId]!!
            val item = ActionedMenuItem(Image(), action)
            menu.addItem(item)
            menuItems[action] = item
        } else {
            val parentAction = ActionManager.findAction(parentId)
            if (parentAction != null) {
                val parentMenu = findMenuForAction(parentAction)!!
                if (parentMenu.subMenu == null) {
                    parentMenu.subMenu = PopupMenu()
                }

                val item = ActionedMenuItem(Image(), action)
                parentMenu.subMenu.addItem(item)
                menuItems[action] = item
            }
        }
    }

    fun findMenuForAction(action: Action) = menuItems[action]
}
