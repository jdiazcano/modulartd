package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.jdiazcano.modulartd.plugins.actions.Action
import com.kotcrab.vis.ui.widget.MenuItem

class ActionedMenuItem(image: Image, val action: Action) : MenuItem(action.description, image) {

    init {
        addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor?) {
                action.perform()
            }
        })
    }

}