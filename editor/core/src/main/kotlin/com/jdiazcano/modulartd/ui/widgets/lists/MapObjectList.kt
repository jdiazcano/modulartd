package com.jdiazcano.modulartd.ui.widgets.lists

import com.jdiazcano.modulartd.beans.MapObject
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.ui.MapObjectView
import com.jdiazcano.modulartd.ui.widgets.TableList
import com.jdiazcano.modulartd.utils.clickListener
import mu.KLogging

class MapObjectList<T: MapObject>(objects: MutableList<T>, clazz: Class<T>): TableList<T, MapObjectView>(objects) {
    companion object: KLogging()

    init {
        Bus.register<T>(clazz, BusTopic.CREATED) {
            addItem(it)
            logger.debug { "Added ${clazz.simpleName}: ${it.name}" }
        }

        Bus.register<kotlin.Unit>(clazz, BusTopic.RESET) {
            clearList()
            logger.debug { "Cleared list of: ${clazz.simpleName}" }
        }

        Bus.register<T>(clazz, BusTopic.DELETED) {
            removeItem(it)
        }

        Bus.register<Resource>(Resource::class.java, BusTopic.LOAD_FINISHED) {
            invalidateList()
        }

        Bus.register<T>(clazz, BusTopic.UPDATED) {
            invalidateList()
        }
    }

    override fun getView(position: Int, lastView: MapObjectView?): MapObjectView {
        val item = getItem(position)
        val view = if (lastView == null) {
            val objectView = MapObjectView(item)
            objectView.clickListener { _, _, _ ->
                Bus.post(getItem(position), BusTopic.SELECTED)
            }
            objectView
        } else {
            lastView
        }
        view.image.swapResource(item.resource)
        view.image.spriteTimer = item.animationTimer
        view.rotation = item.rotationAngle
        view.labelName.setText(item.name)
        return view
    }
}