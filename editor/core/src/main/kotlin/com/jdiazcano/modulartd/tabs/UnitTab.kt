package com.jdiazcano.modulartd.tabs

import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.beans.Unit
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.ui.AnimatedActor
import com.jdiazcano.modulartd.ui.widgets.AnimatedButton
import com.jdiazcano.modulartd.ui.widgets.lists.UnitList
import com.jdiazcano.modulartd.ui.widgets.pickResource
import com.jdiazcano.modulartd.utils.clickListener
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.*

class UnitTab: BaseTab<Unit>(translate("tabs.units", "Units")) {

    private lateinit var splitPane: VisSplitPane
    private val propertiesTable = VisTable(true)
    private val list = UnitList(kodein.instance<Map>().units)

    private val labelName = VisLabel(translate("name", "Name"))
    private val textName = VisValidatableTextField()
    private val labelImage = VisLabel(translate("image", "Image"))
    private val buttonImage = AnimatedButton(AnimatedActor())
    private val labelArmor = VisLabel(translate("armor", "Armor"))
    private val textArmor = VisValidatableTextField()
    private val labelHitpoints = VisLabel(translate("hitpoints", "Hit points"))
    private val textHitpoints = VisValidatableTextField()
    private val labelHPregen = VisLabel(translate("hpregen", "HP regen"))
    private val textHPregen = VisValidatableTextField()
    private val labelMovementSpeed = VisLabel(translate("speed", "Speed"))
    private val textMovementSpeed = VisValidatableTextField()
    private val labelLivesTaken = VisLabel(translate("livestaken", "Lives taken"))
    private val textLivesTaken = VisValidatableTextField()

    /* CheckBoxes */
    private val checkAir = VisCheckBox(translate("air", "Air"))
    private val checkInvisible = VisCheckBox(translate("invisible", "Invisible"))
    private val checkAntiSlow = VisCheckBox(translate("antislow", "Anti slow"))
    private val checkAntiStun = VisCheckBox(translate("antistun", "Anti stun"))

    //private var soundTable: SoundTable<UnitSound>? = null
    //private var coinDropTable: CoinQuantifierTable? = null

    init {
        Bus.register<Unit>(Unit::class.java, BusTopic.SELECTED) {
            updateUI(it)
        }

        buildTable()
    }

    private fun buildTable() {
        setUpValidableForm()
        placeComponents()

        buttonImage.clickListener { _, _, _ ->
            pickResource("Pick", ResourceType.IMAGE) {
                buttonImage.resource = it
                list.notifyDataSetChanged()
            }
        }

        content.add(splitPane).expand().fill()
    }

    override fun save(): Boolean {
        if (list.hasSelection()) {
            val item = list.selectedItem
            item.update(getCurrentObject())
            list.notifyDataSetChanged()
        } else {
            Bus.post(getCurrentObject(), BusTopic.CREATED)
        }
        return true
    }

    override fun reset() {
        if (list.hasSelection()) {
            updateUI(list.selectedItem)
        }
    }

    override fun newItem() {
        list.clearSelection()
    }

    override fun updateUI(item: Unit) {
        textName.text = item.name
        textMovementSpeed.text = item.movementSpeed.toString()
        textHitpoints.text = item.hitPoints.toString()
        textArmor.text = item.armor.toString()
        textHPregen.text = item.hpRegenPerSecond.toString()
        textLivesTaken.text = item.livesTaken.toString()
        buttonImage.resource = item.resource
        buttonImage.rotation = item.rotationAngle
        checkAntiSlow.isChecked = item.antiSlow
        checkAntiStun.isChecked = item.antiStun
        checkAir.isChecked = item.air
        checkInvisible.isChecked = item.invisible
        // soundTable.useSounds(unit.getSoundMap(), UnitSound::class.java)
        // coinDropTable.setCoins(unit.getDrop())
    }

    override fun getCurrentObject(): Unit {
        return Unit(
                textName.text,
                buttonImage.resource,
                buttonImage.rotation,
                textMovementSpeed.text.toFloat(),
                textHitpoints.text.toFloat(),
                textArmor.text.toFloat(),
                checkInvisible.isChecked,
                checkAir.isChecked,
                checkAntiStun.isChecked,
                checkAntiSlow.isChecked,
                textLivesTaken.text.toInt(),
                textHPregen.text.toFloat()
        )
    }

    private fun placeComponents() {
        //Build the table
        propertiesTable.add(labelName).left().padRight(10F)
        propertiesTable.add(textName).padBottom(7F).row()
        propertiesTable.add(labelMovementSpeed).left().padRight(10F)
        propertiesTable.add(textMovementSpeed).row()
        propertiesTable.add(labelImage).left().padRight(10F)
        propertiesTable.add(buttonImage).size(50F).row()
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
        //propertiesTable.add(soundTable).colspan(2).left().row()
        propertiesTable.addSeparator().colspan(2).expandX().fillX().row()
        //propertiesTable.add(coinDropTable).colspan(2).left().row()

        //We must have this tables, else we can't place things at the top
        val tableList = VisTable(true)
        tableList.add(list).fillX().expand().top()
        val tableProp = VisTable(true)
        tableProp.add(propertiesTable).padLeft(17f).fillX().expand().top()

        splitPane = VisSplitPane(tableList, tableProp, false)
        splitPane.setSplitAmount(0.40f)
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