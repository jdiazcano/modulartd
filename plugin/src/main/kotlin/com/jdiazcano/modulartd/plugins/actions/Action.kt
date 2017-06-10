package com.jdiazcano.modulartd.plugins.actions

import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.ui.StageWrapper

/**
 * Class that performs an action, this will be used to create the menus in the GUI. One action will be one menu which
 * will have the action inside it in order to know the shortcut and be displayed correctly.
 *
 * An action can have a parent action that is linked by a name (that name must be unique) so the menus will be able to
 * have submenus and sub-submenus and the whole hierarchy.
 */
abstract class Action(
        val name: String,
        val description: String,
        val shortCut: ShortCut,
        val separator: SeparatorPlace = SeparatorPlace.NONE,
        val icon: Drawable? = null
): Actioned {

    override fun toString(): String {
        return """{
    "name": "$name",
    "description": "$description",
    "shorCut": "$shortCut",
    "separator": "$separator"
}"""
    }
}

enum class SeparatorPlace {
    NONE, ABOVE, BELOW, BOTH
}

interface Actioned {
    fun perform(stage: StageWrapper): Unit
}