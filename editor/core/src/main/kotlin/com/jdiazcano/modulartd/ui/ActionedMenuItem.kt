package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.jdiazcano.modulartd.keys.Action
import com.kotcrab.vis.ui.widget.MenuItem

class ActionedMenuItem(text: String, image: Image, action: Action) : MenuItem(text, image) {

    init {
        addListener {
            action.perform()
        }
    }

}