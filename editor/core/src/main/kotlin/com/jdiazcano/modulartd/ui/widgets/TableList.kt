package com.jdiazcano.modulartd.ui.widgets

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisScrollPane
import com.kotcrab.vis.ui.widget.VisTable

val highlightBackground: Drawable = VisUI.getSkin().getDrawable("list-selection")

abstract class TableList<O : Any, V : Table>(var objects: MutableList<O> = arrayListOf()) : VisTable(), BaseList<O, V> {

    private val items: MutableList<Item<V>> = arrayListOf()

    private var indexDrop = -1
    private var indexTemp = -1
    private var viewDrop: V? = null

    var selectedIndex = -1

    private val scroll = VisScrollPane(this)

    init {
        scroll.setCancelTouchFocus(false)
        scroll.setFlickScroll(false)
        for (i in items.indices) {
            val view = getView(i, null)
            add(view).left().padLeft(10f).expandX().fillX().row()
            this.items.add(Item(items[i].hashCode, view))
        }

        addListener(object : InputListener() {

            override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
                if (keycode == Input.Keys.DOWN && this@TableList.items.size - 1 > selectedIndex && selectedIndex >= -1) {
                    selectItem(selectedIndex + 1)
                } else if (keycode == Input.Keys.UP && selectedIndex > 0) {
                    selectItem(selectedIndex - 1)
                }
                return true
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                indexDrop = findActorIndexByCoordinates(x, y)
                if (indexDrop >= 0) {
                    selectItem(indexDrop)
                    // TODO InfiniteEditor.stage.setKeyboardFocus(this@TableList)
                    viewDrop = getView(indexDrop, null)
                    viewDrop?.pack()
                }
                return true
            }

            override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
                // TODO InfiniteEditor.stage.addActor(viewDrop)
                val position = localToStageCoordinates(Vector2(x, y))
                viewDrop?.setPosition(position.x, position.y)
                super.touchDragged(event, x, y, pointer)
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                indexTemp = findActorIndexByCoordinates(x, y)

                swapItemsIfNeeded()
                cleanupDraggableView()
            }
        })
    }

    override fun getItem(position: Int) = objects[position]

    override fun getCount() = objects.size

    override fun notifyDataSetChanged() {
        for (i in items.indices) {
            if (items[i].hashCode != getItem(i).hashCode()) {
                getView(i, items[i].view).invalidate()
                items[i].hashCode = getItem(i).hashCode()
            }
        }
    }

    override fun addItem(item: O) {
        val index = objects.indexOf(item)
        if (index >= 0) {
            getView(index, items[index].view)
        } else {
            putItem(item)
        }
    }

    fun clearList() {
        objects.forEach { removeItem(it) }
    }

    private fun putItem(obj: O) {
        objects.add(obj)
        val index = objects.indexOf(obj)
        val view = getView(index, null)
        val item = Item(obj.hashCode(), view)
        items.add(item)
        add(view).left().padLeft(10f).expandX().fillX().row()
    }

    override fun removeItem(item: O): Boolean {
        val index = objects.indexOf(item)
        if (index >= 0) {
            objects.remove(item)
            setItems(objects)
            selectedIndex = -1
            return true
        }
        return false
    }

    override fun removeItem(index: Int): O? {
        var item: O? = null
        if (index >= 0 && index < items.size) {
            item = objects.removeAt(index)
            setItems(objects)
            selectedIndex = -1
        }
        return item
    }

    override fun clearSelection() {
        if (selectedIndex != -1) {
            items[selectedIndex].setSelected(false)
            selectedIndex = -1
        }
    }

    override fun hasSelection() = selectedIndex != -1

    override fun selectItem(index: Int) {
        if (hasSelection()) {
            items[selectedIndex].setSelected(false)
        }
        selectedIndex = index
        items[index].setSelected(true)
    }

    override fun selectItem(item: O) = selectItem(objects.indexOf(item))

    // TODO clean up this mess later, it hurts seeing this kind of code in kotlin
    override fun selectView(view: V) {
        for (i in items.indices) {
            if (items[i].view === view) {
                selectItem(i)
                break
            }
        }
    }

    fun setItems(items: MutableList<O>) {
        clearChildren()

        this.items.clear()
        this.objects = items

        for (i in items.indices) {
            val view = getView(i, null)
            add(view).left().padLeft(10f).expandX().fillX().row()
            val item = Item(items[i].hashCode(), view)
            this.items.add(item)
        }
    }

    fun findActorIndexByCoordinates(x: Float, y: Float): Int {
        var index = -1

        var a: Actor? = hit(x, y, true) ?: return index

        while (a!!.parent !is TableList<*, *>) {
            a = a.parent
        }

        for (i in this@TableList.items.indices) {
            if (this@TableList.items[i].view === a) {
                index = i
                break
            }
        }

        return index
    }

    private fun swapItemsIfNeeded() {
        if (indexTemp != -1 && indexDrop != -1 && indexTemp != indexDrop) {
            val toAdd = objects.removeAt(indexDrop)
            objects.add(indexTemp, toAdd)
            selectItem(toAdd)
            indexTemp = -1
            indexDrop = -1

            notifyDataSetChanged()
        }
    }

    private fun cleanupDraggableView() {
        viewDrop?.remove()
        viewDrop = null
    }

    fun invalidateList() {
        items.forEach { it.hashCode = -12345 }
        notifyDataSetChanged()
        if (hasSelection()) {
            selectItem(selectedIndex)
        }
    }

    fun invalidate(index: Int) {

    }

    val selectedItem: O get() = getItem(selectedIndex)
}

class Item<V : Table>(
        var hashCode: Int,
        var view: V? = null
) {
    fun setSelected(selected: Boolean) {
        if (selected) {
            view?.background = highlightBackground
        } else {
            view?.background = null
        }
    }
}