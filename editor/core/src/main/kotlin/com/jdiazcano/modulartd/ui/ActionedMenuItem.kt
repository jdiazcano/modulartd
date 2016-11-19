package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jdiazcano.modulartd.plugins.actions.Action
import com.kotcrab.vis.ui.widget.MenuItem

class ActionedMenuItem(image: Image, action: Action) : MenuItem(action.description, image) {

    init {

    }

}