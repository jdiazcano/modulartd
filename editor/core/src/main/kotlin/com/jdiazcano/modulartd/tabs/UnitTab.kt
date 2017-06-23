package com.jdiazcano.modulartd.tabs

import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.*
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.beans.Unit
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.ui.widgets.AnimatedButton
import com.jdiazcano.modulartd.ui.widgets.SoundChooser
import com.jdiazcano.modulartd.ui.widgets.lists.MapObjectList
import com.jdiazcano.modulartd.ui.widgets.pickResource
import com.jdiazcano.modulartd.ui.widgets.rotatable
import com.jdiazcano.modulartd.utils.clickListener
import com.jdiazcano.modulartd.utils.scrollable
import com.jdiazcano.modulartd.utils.sneakyChange
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.spinner.Spinner
import mu.KLogging
import java.util.*

class UnitTab: BaseTab<Unit>(translate("tabs.units"), true) {
    companion object : KLogging()

    private lateinit var splitPane: VisSplitPane
    private val propertiesTable = VisTable(true)
    private val list = MapObjectList(kodein.instance<Map>().units, Unit::class.java)

    private val labelName = VisLabel(translate("name"))
    private val textName = VisValidatableTextField()
    private val labelImage = VisLabel(translate("image"))
    private val buttonImage = AnimatedButton()
    private val labelArmor = VisLabel(translate("armor"))
    private val textArmor = VisValidatableTextField()
    private val labelHitpoints = VisLabel(translate("hitpoints"))
    private val textHitpoints = VisValidatableTextField()
    private val labelHPregen = VisLabel(translate("hpregen"))
    private val textHPregen = VisValidatableTextField()
    private val labelMovementSpeed = VisLabel(translate("speed"))
    private val textMovementSpeed = VisValidatableTextField()
    private val labelLivesTaken = VisLabel(translate("livestaken"))
    private val textLivesTaken = VisValidatableTextField()
    private val labelAnimationTime = VisLabel(translate("animationtime"))
    private val spinnerAnimationTime = Spinner("", kodein.instance("float"))

    /* CheckBoxes */
    private val checkAir = VisCheckBox(translate("air"))
    private val checkInvisible = VisCheckBox(translate("invisible"))
    private val checkAntiSlow = VisCheckBox(translate("antislow"))
    private val checkAntiStun = VisCheckBox(translate("antistun"))

    private var soundTable: SoundChooser<UnitEvent> = SoundChooser()
    //private var coinDropTable: CoinQuantifierTable? = null

    init {
        Bus.register<Unit>(BusTopic.SELECTED) {
            updateUI(it)
        }

        buildTable()
    }

    private fun buildTable() {
        setUpValidableForm()
        placeComponents()
        addUpdateListeners()
        addTextFieldsForEvents()

        content.add(splitPane).expand().fill()
    }

    private fun addTextFieldsForEvents() {
        textFields += textName
        textFields += textMovementSpeed
        textFields += textHitpoints
        textFields += textArmor
        textFields += textHPregen
        textFields += textLivesTaken
    }

    /**
     * These are the listeners that will be listening for changes in the textviews and will
     * update the selected item in the list
     */
    private fun addUpdateListeners() {
        buttonImage.clickListener { _, _, _ ->
            pickResource(translate("pick"), ResourceType.IMAGE) {
                buttonImage.resource = it
                list.selectedItem.resource = it
                list.notifyDataSetChanged()
                isDirty = true
            }
        }

        textArmor.sneakyChange {
            list.selectedItem.armor = textArmor.text.toFloat()
            isDirty = true
        }
        textHPregen.sneakyChange {
            list.selectedItem.hpRegenPerSecond = textHPregen.text.toFloat()
            isDirty = true
        }
        textHitpoints.sneakyChange {
            list.selectedItem.hitPoints = textHitpoints.text.toFloat()
            isDirty = true
        }
        textLivesTaken.sneakyChange {
            list.selectedItem.livesTaken = textLivesTaken.text.toInt()
            isDirty = true
        }
        textMovementSpeed.sneakyChange {
            list.selectedItem.movementSpeed = textMovementSpeed.text.toFloat()
            isDirty = true
        }
        textName.sneakyChange {
            list.selectedItem.name = textName.text
            list.invalidateSelected()
            isDirty = true
        }
        checkAir.sneakyChange {
            list.selectedItem.air = checkAir.isChecked
            isDirty = true
        }
        checkAntiSlow.sneakyChange {
            list.selectedItem.antiSlow = checkAntiSlow.isChecked
            isDirty = true
        }
        checkAntiStun.sneakyChange {
            list.selectedItem.antiStun = checkAntiStun.isChecked
            isDirty = true
        }
        checkInvisible.sneakyChange {
            list.selectedItem.invisible = checkInvisible.isChecked
            isDirty = true
        }

        spinnerAnimationTime.sneakyChange {
            val time = spinnerAnimationTime.model.text.toFloat()
            list.selectedItem.animationTimer = time
            buttonImage.animationTimer = time
            logger.debug { "Animation timer changed to $time" }
            list.notifyDataSetChanged()
            isDirty = true
        }

        //TODO set dirty on the SoundChooser
    }

    override fun newItem() {
        val unit = Unit("Unit ${Random().nextInt(10000)}", Resource())
        Bus.post(unit, BusTopic.CREATED)
        list.selectItem(unit)
        updateUI(unit)
    }

    override fun updateUI(item: Unit) {
        disableProgrammaticEvents()

        textName.text = item.name
        textMovementSpeed.text = item.movementSpeed.toString()
        textHitpoints.text = item.hitPoints.toString()
        textArmor.text = item.armor.toString()
        textHPregen.text = item.hpRegenPerSecond.toString()
        textLivesTaken.text = item.livesTaken.toString()
        buttonImage.mapObject = item
        checkAntiSlow.isChecked = item.antiSlow
        checkAntiStun.isChecked = item.antiStun
        checkAir.isChecked = item.air
        checkInvisible.isChecked = item.invisible
        soundTable.update(item)
        // coinDropTable.setCoins(unit.getDrop())

        enableProgrammaticEvents()
    }



    private fun placeComponents() {
        //Build the table
        propertiesTable.add(labelName).left().padRight(10F)
        propertiesTable.add(textName).padBottom(7F).row()
        propertiesTable.add(labelMovementSpeed).left().padRight(10F)
        propertiesTable.add(textMovementSpeed).row()
        propertiesTable.add(labelImage).left().padRight(10F)
        propertiesTable.add(buttonImage.rotatable()).size(50F).row()
        propertiesTable.add(labelAnimationTime).left().padRight(10F)
        propertiesTable.add(spinnerAnimationTime).row()
        propertiesTable.add(labelHitpoints).left().padRight(10F)
        propertiesTable.add(textHitpoints).row()
        propertiesTable.add(labelArmor).left().padRight(10F)
        propertiesTable.add(textArmor).row()
        propertiesTable.add(labelHPregen).left().padRight(10F)
        propertiesTable.add(textHPregen).row()
        propertiesTable.add(labelLivesTaken).left().padRight(10F)
        propertiesTable.add(textLivesTaken).row()

        propertiesTable.add(checkAir).left()
        propertiesTable.add(checkAntiSlow).left().row()
        propertiesTable.add(checkInvisible).left()
        propertiesTable.add(checkAntiStun).left().row()
        propertiesTable.addSeparator().colspan(2).expandX().fillX().row()
        propertiesTable.add(soundTable).colspan(2).left().row()
        propertiesTable.addSeparator().colspan(2).expandX().fillX().row()
        //propertiesTable.add(coinDropTable).colspan(2).left().row()

        //We must have this tables, else we can't place things at the top
        val tableList = VisTable(true)
        tableList.add(list).fillX().expand().top()
        val tableProp = VisTable(true)
        tableProp.add(propertiesTable).padLeft(17F).fillX().expand().top()

        splitPane = VisSplitPane(tableList, tableProp.scrollable(), false)
        splitPane.setSplitAmount(0.40F)
    }

    private fun setUpValidableForm() {
        validator.notEmpty(textName, "Name can't be empty")
        validator.valueGreaterThan(textArmor, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textHitpoints, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textHPregen, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textMovementSpeed, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textLivesTaken, "It needs to be a positive value", 0F, true)
    }

}