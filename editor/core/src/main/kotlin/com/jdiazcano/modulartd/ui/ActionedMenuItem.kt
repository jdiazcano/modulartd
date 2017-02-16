package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.jdiazcano.modulartd.keys.KeyPrinter
import com.jdiazcano.modulartd.keys.KeyPrinters
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.kotcrab.vis.ui.widget.MenuItem

class ActionedMenuItem(image: Image, val action: Action) : MenuItem(action.description, image) {

    init {
        setShortcut(KeyPrinters.print(action.shortCut))

        addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent?, actor: Actor) {
                action.perform(StageWrapperImpl(actor.stage))
            }
        })
    }

}