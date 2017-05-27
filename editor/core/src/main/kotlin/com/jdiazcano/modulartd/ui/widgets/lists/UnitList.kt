package com.jdiazcano.modulartd.ui.widgets.lists

import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.beans.Unit
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.ui.MapObjectView
import com.jdiazcano.modulartd.ui.widgets.TableList

class UnitList(units: MutableList<Unit>): TableList<Unit, MapObjectView>(units) {
    init {
        Bus.register<Resource>(Resource::class.java, BusTopic.LOAD_FINISHED) {
            invalidateList()
        }
    }

    override fun getView(position: Int, lastView: MapObjectView?): MapObjectView {
        return MapObjectView(getItem(position))
    }
}