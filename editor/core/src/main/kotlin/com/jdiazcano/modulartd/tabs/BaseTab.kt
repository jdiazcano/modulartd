package com.jdiazcano.modulartd.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.util.form.FormValidator
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab

abstract class BaseTab(
        val title: String,
        scriptable: Boolean = false
): Tab(true, false) {
    private val fullContent = VisTable()
    protected val content = VisTable()
    protected val save = VisTextButton(translate("save", "Save"))
    protected val cancel = VisTextButton(translate("cancel", "Cancel"))
    protected val new = VisTextButton(translate("new", "New"))
    protected val script = VisTextButton(translate("script", "Script"))
    protected val validator = FormValidator(save, VisLabel("TODO")) //TODO create a global label at the bottom

    init {
        val buttons = VisTable()

        buttons.add(new).padRight(10F).expandX().right()
        if (scriptable) {
            buttons.add(script).padRight(10F)
        }
        buttons.add(save).padRight(10F)
        buttons.add(cancel)

        fullContent.add(content).expand().fill().row()
        fullContent.add(buttons).expandX().right().padBottom(10F)
    }

    override fun getContentTable(): Table {
        return fullContent
    }

    override fun getTabTitle() = title
}