package com.jdiazcano.modulartd.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.utils.clickListener
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.util.form.FormValidator
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab

abstract class BaseTab<T>(
        val title: String,
        scriptable: Boolean = false
): Tab(true, false) {
    private val fullContent = VisTable()
    protected val content = VisTable()
    protected val save = VisTextButton(translate("save", "Save"))
    protected val cancel = VisTextButton(translate("cancel", "Cancel"))
    protected val new = VisTextButton(translate("new", "New"))
    protected val script = VisTextButton(translate("script", "Script"))
    protected val validator = FormValidator(save, kodein.instance("globalMessageLabel"))

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

        save.clickListener { inputEvent, x, y -> save() }
        cancel.clickListener { inputEvent, x, y -> reset() }
        new.clickListener { inputEvent, x, y -> newItem() }
    }

    /**
     * Resets the current state (and this will set the dirty to false)
     */
    abstract fun reset()

    /**
     * Creates a new item. Right now a new item is done by just unselecting the list and then you can save your current one.
     * I'm not sure if I should just create an empty one and save it to the list and select it! This seems more common to do
     */
    abstract fun newItem()

    abstract fun updateUI(item: T)

    abstract fun getCurrentObject(): T

    override fun getContentTable(): Table {
        return fullContent
    }

    override fun getTabTitle() = title
}