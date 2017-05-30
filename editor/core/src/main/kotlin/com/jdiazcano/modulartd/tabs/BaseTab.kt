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
    protected val new = VisTextButton(translate("new", "New"))
    protected val script = VisTextButton(translate("script", "Script"))
    protected val validator = FormValidator(save, kodein.instance("globalMessageLabel"))

    init {
        val buttons = VisTable()

        buttons.add(new).padRight(10F).expandX().right()
        if (scriptable) {
            buttons.add(script).padRight(10F)
        }

        fullContent.add(content).expand().fill().row()
        fullContent.add(buttons).expandX().right().padBottom(10F)

        new.clickListener { _, _, _ -> newItem() }
    }


    /**
     * Creates a new item. Right now a new item is done by just unselecting the list and then you can save your current one.
     * I'm not sure if I should just create an empty one and save it to the list and select it! This seems more common to do
     */
    abstract fun newItem()

    /**
     * Updates the table accordingly to the item passed as argument. The tabs will have a table that contains all the
     * information when selecting an item so this is the method that will be called when the selection changes for example.
     */
    abstract fun updateUI(item: T)

    override fun getContentTable(): Table {
        return fullContent
    }

    override fun getTabTitle() = title
}