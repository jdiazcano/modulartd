package com.jdiazcano.modulartd.ui.widgets

import com.jdiazcano.modulartd.beans.MapObject
import com.jdiazcano.modulartd.ui.MapObjectView

/**
 * Created by Javi on 19/05/2017.
 */
class MapObjectList(objects: MutableList<MapObject>) : TableList<MapObject, MapObjectView>(objects) {
    override fun getView(position: Int, lastView: MapObjectView?): MapObjectView {
        return MapObjectView(getItem(position))
    }
}