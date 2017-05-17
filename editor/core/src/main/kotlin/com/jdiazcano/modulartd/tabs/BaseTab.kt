package com.jdiazcano.modulartd.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.jdiazcano.modulartd.config.Translations
import com.kotcrab.vis.ui.util.form.FormValidator
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab

abstract class BaseTab(val title: String): Tab(true, false) {
    protected val content = VisTable()
    protected val save = VisTextButton(Translations.of("save", "Save"))
    protected val cancel = VisTextButton(Translations.of("cancel", "Cancel"))
    protected val script = VisTextButton(Translations.of("script", "Script"))
    protected val validator = FormValidator(save, VisLabel("TODO")) //TODO create a global label at the bottom

    override fun getContentTable(): Table {
        return content
    }

    override fun getTabTitle() = title
}