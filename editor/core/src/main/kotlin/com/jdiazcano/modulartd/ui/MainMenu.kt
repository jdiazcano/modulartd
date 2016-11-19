package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.ActionManager
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.PopupMenu

class MainMenu(title: String?) : Menu(title) {
    private val menus: MutableMap<Action, ActionedMenuItem> = mutableMapOf()

    fun createMenu(action: Action) {
        val parentAction = ActionManager.findParent(action)
        if (parentAction != null) {
            val parentMenu = findMenuForAction(parentAction)!!
            if (parentMenu.subMenu == null) {
                parentMenu.subMenu = PopupMenu(parentAction.description)
            }

            val item = ActionedMenuItem(Image(), action)
            parentMenu.subMenu.addItem(item)
            menus[action] = item
        }
    }

    fun findMenuForAction(action: Action) = menus[action]
}