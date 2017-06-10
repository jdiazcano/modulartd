package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.jdiazcano.modulartd.keys.KeyPrinters
import com.jdiazcano.modulartd.plugins.actions.Action
import com.kotcrab.vis.ui.widget.MenuItem

class ActionedMenuItem(val action: Action) : MenuItem(action.description, Image(action.icon)) {

    init {
        setShortcut(KeyPrinters.print(action.shortCut))

        addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor) {
                action.perform(StageWrapperImpl(actor.stage))
            }
        })
    }

}