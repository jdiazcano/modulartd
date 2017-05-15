package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.jdiazcano.modulartd.config.Translations
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.actions.SeparatorPlace
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.kotcrab.vis.ui.widget.VisWindow

class AboutPlugin : Plugin {
    override fun getName() = "About plugin"

    override fun getVersion() = 1

    override fun getMinimumCompatibleVersion() = 1

    override fun getPrefix() = "About"

    override fun onLoad() {}

    override fun onUnload() {}

    @RegisterAction(Menus.HELP)
    fun About() = AboutAction()
}

class AboutAction : Action("help.about", Translations.of("help.about", "About"), ShortCut(Input.Keys.Q, Modifiers(control = true)), SeparatorPlace.ABOVE) {
    override fun perform(stage: StageWrapper) {
        val window = VisWindow(Translations.of("help.about", "About"))
        window.closeOnEscape()
        window.addCloseButton()
        window.add("By me!!") // TODO add a proper about
        window.setCenterOnAdd(true)

        stage.addWindow(window)
    }
}