package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jdiazcano.modulartd.ActionManager
import com.jdiazcano.modulartd.ParentedAction
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.SeparatorPlace
import com.jdiazcano.modulartd.utils.getOrThrow
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.MenuBar
import com.kotcrab.vis.ui.widget.PopupMenu

class MainMenu : MenuBar() {
    private val menuItems: MutableMap<Action, ActionedMenuItem> = mutableMapOf()
    private val menus = mapOf(
            Menus.FILE to Menu("File"),
            Menus.VIEW to Menu("View"),
            Menus.EDIT to Menu("Edit"),
            Menus.GAME to Menu("Game"),
            Menus.HELP to Menu("Help")
    )
    init {
        menus.forEach { addMenu(it.value) }
        Bus.register(ParentedAction::class.java, BusTopic.ACTION_REGISTERED) {
            createMenu(it)
        }
    }

    fun createMenu(parentedAction: ParentedAction) {
        val (action, parentId) = parentedAction
        val separator = action.separator
        if (parentId in menus) {
            val menu = menus[parentId]!!
            val item = ActionedMenuItem(Image(), action)
            if (separator == SeparatorPlace.ABOVE || separator == SeparatorPlace.BOTH) {
                menu.addSeparator()
            }
            menu.addItem(item)
            if (separator == SeparatorPlace.BELOW || separator == SeparatorPlace.BOTH) {
                menu.addSeparator()
            }
            menuItems[action] = item
        } else {
            val parentAction = ActionManager.findAction(parentId)
            if (parentAction != null) {
                val parentMenu = findMenuForAction(parentAction)
                if (parentMenu.subMenu == null) {
                    parentMenu.subMenu = PopupMenu()
                }

                val item = ActionedMenuItem(Image(), action)
                parentMenu.subMenu.addItem(item)
                if (separator == SeparatorPlace.ABOVE || separator == SeparatorPlace.BOTH) {
                    parentMenu.subMenu.addSeparator()
                }
                parentMenu.subMenu.addItem(item)
                if (separator == SeparatorPlace.BELOW || separator == SeparatorPlace.BOTH) {
                    parentMenu.subMenu.addSeparator()
                }
                menuItems[action] = item
            }
        }
    }

    fun findMenuForAction(action: Action) = menuItems.getOrThrow(action)
}
