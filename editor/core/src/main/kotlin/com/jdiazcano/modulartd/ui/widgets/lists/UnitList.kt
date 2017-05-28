package com.jdiazcano.modulartd.ui.widgets.lists

import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.beans.Unit
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.ui.MapObjectView
import com.jdiazcano.modulartd.ui.widgets.TableList
import com.jdiazcano.modulartd.utils.clickListener
import mu.KLogging

class UnitList(units: MutableList<Unit>): TableList<Unit, MapObjectView>(units) {
    companion object: KLogging()

    init {
        Bus.register<Unit>(Unit::class.java, BusTopic.CREATED) {
            addItem(it)
            logger.debug { "Added unit: ${it.name}" }
        }



        Bus.register<kotlin.Unit>(Unit::class.java, BusTopic.RESET) {
            clearList()
            logger.debug { "Cleared list of units" }
        }

        Bus.register<Resource>(Resource::class.java, BusTopic.LOAD_FINISHED) {
            invalidateList()
        }
    }

    override fun getView(position: Int, lastView: MapObjectView?): MapObjectView {
        val item = getItem(position)
        val view = if (lastView == null) {
            val objectView = MapObjectView(item)
            objectView.clickListener { _, _, _ -> Bus.post(getItem(position), BusTopic.SELECTED) }
            objectView
        } else {
            lastView
        }
        view.image.swapResource(item.resource)
        view.rotation = item.rotationAngle
        view.labelName.setText(item.name)
        return view
    }
}