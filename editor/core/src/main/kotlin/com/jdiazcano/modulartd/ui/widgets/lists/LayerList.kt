package com.jdiazcano.modulartd.ui.widgets.lists

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.jdiazcano.modulartd.beans.Layer
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.ui.widgets.LayerView
import com.jdiazcano.modulartd.ui.widgets.TableList
import com.kotcrab.vis.ui.widget.VisCheckBox
import mu.KLogging

class LayerList(items: MutableList<Layer>) : TableList<Layer, LayerView>(items) {

    companion object: KLogging()

    init {
        Bus.register<Layer>(Layer::class.java, BusTopic.CREATED) {
            addItem(it)
            logger.debug { "Added layer: ${it.name}" }
        }

        Bus.register<kotlin.Unit>(Layer::class.java, BusTopic.RESET) {
            clearList()
            logger.debug { "Cleared list of: Layer" }
        }

        Bus.register<Layer>(Layer::class.java, BusTopic.LOAD_FINISHED) {
            invalidateList()
        }
    }

    override fun getView(position: Int, lastView: LayerView?): LayerView {
        var lastView = lastView
        val layer = getItem(position)
        if (lastView == null) {
            lastView = LayerView(layer)
            lastView.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    // TODO I don't remember what's this lol InfiniteEditor.stage.setKeyboardFocus(this@LayerList)
                    if (event!!.target.parent !is VisCheckBox) {
                        var a = event.target
                        while (a.parent !is TableList<*, *>) {
                            a = a.parent
                        }
                        selectView(a as LayerView)
                    }
                }
            })
        }

        lastView.layer = layer
        lastView.name.setText(layer.name)
        lastView.check.isChecked = layer.visible

        return lastView
    }

}