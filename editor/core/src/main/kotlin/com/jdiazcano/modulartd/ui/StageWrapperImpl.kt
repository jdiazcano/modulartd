package com.jdiazcano.modulartd.ui

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.jdiazcano.modulartd.plugins.ui.StageWrapper

class StageWrapperImpl(private val stage: Stage) : StageWrapper {
    override fun addWindow(table: Table) {
        stage.addActor(table)
    }
}