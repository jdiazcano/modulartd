package com.jdiazcano.modulartd.tabs

import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.ui.widgets.CoinEditor
import com.jdiazcano.modulartd.utils.sneakyChange
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.VisScrollPane
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisValidatableTextField

class GameTab: BaseTab<Map>(translate("tabs.game"), true, false) {

    private val superTable =  VisTable(true)
    private val scroll = VisScrollPane(superTable)

    private val textName = VisValidatableTextField()
    private val textMaker = VisValidatableTextField()
    private val textDescription = VisValidatableTextField()
    private val textAuthorNotes = VisValidatableTextField()

    private val coinEditor = CoinEditor(kodein.instance<Map>().coins)

    private val textTotalUnitsMap = VisValidatableTextField()
    private val textInterestRatio = VisValidatableTextField()
    private val textTurretSellProfit = VisValidatableTextField()

    init {
        Bus.register<Map>(BusTopic.MAP_LOAD) {
            updateUI(it)
        }

        setUpValidableForm()
        placeComponents()
        addUpdateListeners()
        addTextFieldsForEvents()

        //scroll.setScrollingDisabled(true, false)

        content.add("Game properties").expandX().center().row()
        content.add(scroll).expand().top().left().pad(30F)
    }

    private fun addTextFieldsForEvents() {
        textFields += textName
        textFields += textDescription
        textFields += textMaker
        textFields += textAuthorNotes
        textFields += textTurretSellProfit
        textFields += textTotalUnitsMap
        textFields += textInterestRatio
    }

    private fun addUpdateListeners() {
        val map = kodein.instance<Map>()
        textName.text = map.name
        textName.sneakyChange {
            map.name = textName.text
            game.dirty = true
        }

        textMaker.text = map.author
        textMaker.sneakyChange {
            map.author = textMaker.text
            game.dirty = true
        }

        textDescription.text = map.description
        textDescription.sneakyChange {
            map.description = textDescription.text
            game.dirty = true
        }

        textAuthorNotes.text = map.authorNotes
        textAuthorNotes.sneakyChange {
            map.authorNotes = textAuthorNotes.text
            game.dirty = true
        }

        textTotalUnitsMap.text = map.unitCount.toString()
        textTotalUnitsMap.sneakyChange {
            map.unitCount = textTotalUnitsMap.text.toInt()
            game.dirty = true
        }

        textInterestRatio.text = map.interestRatio.toString()
        textInterestRatio.sneakyChange {
            map.interestRatio = textInterestRatio.text.toFloat()
            game.dirty = true
        }

        textTurretSellProfit.text = map.turretSellProfit.toString()
        textTurretSellProfit.sneakyChange {
            map.turretSellProfit = textTurretSellProfit.text.toFloat()
            game.dirty = true
        }
    }

    private fun placeComponents() {
        superTable.add(translate("name")).left();            superTable.add(textName).row()
        superTable.add(translate("author")).left();          superTable.add(textMaker).row()
        superTable.add(translate("description")).left();     superTable.add(textDescription).row()
        superTable.add(translate("authorNotes")).left();     superTable.add(textAuthorNotes).row()
        superTable.add(translate("unitsMap")).left();        superTable.add(textTotalUnitsMap).row()
        superTable.add(translate("interestRatio")).left();   superTable.add(textInterestRatio).row()
        superTable.add(translate("turretSellProfit")).left();superTable.add(textTurretSellProfit).row()
        superTable.add(coinEditor).colspan(999).expandX().fillX().height(300F)
    }

    private fun setUpValidableForm() {
        validator.notEmpty(textName, "Name can't be empty")
        validator.notEmpty(textMaker, "Maker can't be empty")
        validator.notEmpty(textDescription, "Description can't be empty")
        // Notes CAN be empty!!! validator.notEmpty(textAuthorNotes, "Name can't be empty")
        validator.valueGreaterThan(textTotalUnitsMap, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textInterestRatio, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textTurretSellProfit, "It needs to be a positive value", 0F, true)
    }

    override fun newItem() {
        throw NotImplementedError("This is something that should never be called!!!!")
    }

    override fun updateUI(item: Map) {
        disableProgrammaticEvents()

        textName.text = item.name
        textDescription.text = item.description
        textMaker.text = item.author
        textAuthorNotes.text = item.authorNotes

        textTurretSellProfit.text = item.turretSellProfit.toString()
        textTotalUnitsMap.text = item.unitCount.toString()
        textInterestRatio.text = item.interestRatio.toString()

        enableProgrammaticEvents()
    }

}