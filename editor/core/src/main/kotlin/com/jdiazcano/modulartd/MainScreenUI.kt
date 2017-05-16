package com.jdiazcano.modulartd

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Value
import com.jdiazcano.modulartd.tabs.*
import com.jdiazcano.modulartd.ui.widgets.HorizontalToggleTabbedPane
import com.jdiazcano.modulartd.utils.addListener
import com.kotcrab.vis.ui.widget.VisTable

class MainScreenUI(parentActor: Table) : VisTable() {
    private val creatorsTabbedPane = HorizontalToggleTabbedPane()
    private val creatorsTable = VisTable()
    private val game = VisTable()

    init {
        creatorsTabbedPane.addListener {
            creatorsTable.clear()
            creatorsTable.add(it.contentTable).expand().fill()
        }
        creatorsTabbedPane.add(GameTab())
        creatorsTabbedPane.add(TileTab())
        creatorsTabbedPane.add(MobTab())
        creatorsTabbedPane.add(TurretTab())
        creatorsTabbedPane.add(LevelTab())

        val leftSide = VisTable()
        leftSide.add(creatorsTabbedPane.table).expandX().top().center().row()
        leftSide.add(creatorsTable).expand().fill()

        add(leftSide).expandY().fillY().width(Value.percentWidth(0.25F, parentActor))
        add(game).expand().fill()
    }
}