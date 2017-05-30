package com.jdiazcano.modulartd.tabs

import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.beans.Turret
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.ui.widgets.lists.MapObjectList
import com.jdiazcano.modulartd.utils.translate

class TurretTab: BaseTab<Turret>(translate("tabs.turrets", "Turrets")) {
    private val list = MapObjectList(kodein.instance<Map>().turrets, Turret::class.java)


    override fun newItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateUI(item: Turret) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}