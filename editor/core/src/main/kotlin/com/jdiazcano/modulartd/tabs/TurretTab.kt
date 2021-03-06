package com.jdiazcano.modulartd.tabs

import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.*
import com.jdiazcano.modulartd.beans.Map
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
import java.util.*

class TurretTab: BaseTab<Turret>(translate("tabs.turrets"), true) {
    private val list = MapObjectList(kodein.instance<Map>().turrets, Turret::class.java)

    private lateinit var splitPane: VisSplitPane
    private val propertiesTable = VisTable(true)

    private val labelName = VisLabel(translate("label.name"))
    private val textName = VisValidatableTextField()
    private val labelImage = VisLabel(translate("label.image"))
    private val buttonImage = AnimatedButton()
    private val labelBulletImage = VisLabel(translate("label.bulletimage"))
    private val buttonBulletImage = AnimatedButton()
    private val labelDamage = VisLabel(translate("label.damage"))
    private val textDamage = VisValidatableTextField()
    private val labelAttackSpeed = VisLabel(translate("label.attackspeed"))
    private val textAttackSpeed = VisValidatableTextField()
    private val labelRange = VisLabel(translate("label.range"))
    private val textRange = VisValidatableTextField()
    private val labelTargets = VisLabel(translate("label.numberoftargets"))
    private val textTargets = VisValidatableTextField()
    private val labelSplash = VisLabel(translate("label.splashradius"))
    private val textSplash = VisValidatableTextField()

    /* CheckBoxes */
    private val checkAttackAir = VisCheckBox(translate("check.attack.air"))
    private val checkViewInvisible = VisCheckBox(translate("check.view.invisible"))
    private val checkStun = VisCheckBox(translate("check.stun"))
    private val labelStunDuration = VisLabel(translate("stun.duration"))
    private val textStunDuration = VisValidatableTextField()
    private val tableStun = VisTable(true)
    private val containerStun = CollapsibleWidget(tableStun)
    private val labelStunChance = VisLabel(translate("stun.chance"))
    private val textStunChance = VisValidatableTextField()
    private val checkSlow = VisCheckBox(translate("check.slow"))
    private val tableSlow = VisTable(true)
    private val containerSlow = CollapsibleWidget(tableSlow)
    private val labelSlowDuration = VisLabel(translate("slow.duration"))
    private val textSlowDuration = VisValidatableTextField()
    private val labelSlowChance = VisLabel(translate("slow.chance"))
    private val textSlowChance = VisValidatableTextField()
    private val labelSlowPercent = VisLabel(translate("slow.percent"))
    private val textSlowPercent = VisValidatableTextField()
    private val checkCriticalStrike = VisCheckBox(translate("check.criticalstrike"))
    private val tableCrit = VisTable(true)
    private val containerCrit = CollapsibleWidget(tableCrit)
    private val labelCriticalChance = VisLabel(translate("critical.chance"))
    private val textCriticalChance = VisValidatableTextField()
    private val labelCriticalMultiplier = VisLabel(translate("critical.multiplier"))
    private val textCriticalMultiplier = VisValidatableTextField()

    private var soundTable: SoundChooser<TurretEvent> = SoundChooser()
    // private val coinCostTable: CoinQuantifierTable? = null
    // private val updaterTable: TurretUpdaterTable? = null

    init {
        Bus.register<Turret>(BusTopic.SELECTED) {
            updateUI(it)
        }

        placeComponents()
        setUpValidatableForm()
        addChangeListeners()
        addTextFieldsForEvents()

        content.add(splitPane).expand().fill()
    }

    private fun addTextFieldsForEvents() {
        textFields += textName
        textFields += textDamage
        textFields += textRange
        textFields += textAttackSpeed
        textFields += textTargets
        textFields += textSplash
        textFields += textDamage
        textFields += textDamage
        textFields += textDamage
        textFields += textDamage
        textFields += textStunDuration
        textFields += textStunChance
        textFields += textCriticalMultiplier
        textFields += textCriticalChance
        textFields += textSlowPercent
        textFields += textSlowChance
        textFields += textSlowDuration
    }

    private fun addChangeListeners() {
        buttonImage.clickListener { _, _, _ ->
            pickResource(translate("pick"), ResourceType.IMAGE) {
                buttonImage.resource = it
                list.selectedItem.resource = it
                list.notifyDataSetChanged()
                game.dirty = true
            }
        }

        buttonBulletImage.clickListener { _, _, _ ->
            pickResource(translate("pick"), ResourceType.IMAGE) {
                buttonBulletImage.resource = it
                list.selectedItem.bullet.resource = it
                list.notifyDataSetChanged()
                game.dirty = true
            }
        }

        containerStun.isCollapsed = true
        checkStun.sneakyChange {
            containerStun.isCollapsed = !containerStun.isCollapsed
            textStunChance.isValidationEnabled = checkStun.isChecked
            textStunDuration.isValidationEnabled = checkStun.isChecked
            game.dirty = true
        }

        containerCrit.isCollapsed = true
        checkCriticalStrike.sneakyChange {
            containerCrit.isCollapsed = !containerCrit.isCollapsed
            textCriticalChance.isValidationEnabled = checkCriticalStrike.isChecked
            textCriticalMultiplier.isValidationEnabled = checkCriticalStrike.isChecked
            game.dirty = true
        }

        containerSlow.isCollapsed = true
        checkSlow.sneakyChange {
            containerSlow.isCollapsed = !containerSlow.isCollapsed
            textSlowDuration.isValidationEnabled = checkSlow.isChecked
            textSlowChance.isValidationEnabled = checkSlow.isChecked
            textSlowPercent.isValidationEnabled = checkSlow.isChecked
            game.dirty = true
        }

        textName.sneakyChange {
            list.selectedItem.name= textName.text
            list.notifyDataSetChanged()
            game.dirty = true
        }
        textDamage.sneakyChange {
            list.selectedItem.damage = textDamage.text.toFloat()
            game.dirty = true
        }
        textAttackSpeed.sneakyChange {
            list.selectedItem.attackSpeed = textAttackSpeed.text.toFloat()
            game.dirty = true
        }
        textRange.sneakyChange {
            list.selectedItem.range = textRange.text.toFloat()
            game.dirty = true
        }
        textTargets.sneakyChange {
            list.selectedItem.numberOfTargets = textTargets.text.toInt()
            game.dirty = true
        }
        textSplash.sneakyChange {
            list.selectedItem.splashRadius = textSplash.text.toFloat()
            game.dirty = true
        }
        textStunDuration.sneakyChange {
            list.selectedItem.stunDuration = textStunDuration.text.toFloat()
            game.dirty = true
        }
        textStunChance.sneakyChange {
            list.selectedItem.stunChance = textStunChance.text.toFloat()
            game.dirty = true
        }
        textSlowChance.sneakyChange {
            list.selectedItem.slowChance = textSlowChance.text.toFloat()
            game.dirty = true
        }
        textSlowDuration.sneakyChange {
            list.selectedItem.slowDuration = textSlowDuration.text.toFloat()
            game.dirty = true
        }
        textSlowPercent.sneakyChange {
            list.selectedItem.slowPercent = textSlowPercent.text.toFloat()
            game.dirty = true
        }
        textCriticalChance.sneakyChange {
            list.selectedItem.critChance = textCriticalChance.text.toFloat()
            game.dirty = true
        }
        textCriticalMultiplier.sneakyChange {
            list.selectedItem.critMultiplier = textCriticalMultiplier.text.toFloat()
            game.dirty = true
        }
        checkStun.sneakyChange {
            list.selectedItem.canStun = checkStun.isChecked
            game.dirty = true
        }
        checkSlow.sneakyChange {
            list.selectedItem.canSlow = checkSlow.isChecked
            game.dirty = true
        }
        checkCriticalStrike.sneakyChange {
            list.selectedItem.canCrit = checkCriticalStrike.isChecked
            game.dirty = true
        }
    }

    fun setUpValidatableForm() {
        validator.notEmpty(textName, "Name can't be empty") // TODO translate these strings pleaseee
        validator.valueGreaterThan(textDamage, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textAttackSpeed, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textRange, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textTargets, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textSplash, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textStunDuration, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textStunChance, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textSlowDuration, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textSlowChance, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textSlowPercent, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textCriticalChance, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textCriticalMultiplier, "It needs to be a positive value", 0F, true)
    }

    fun placeComponents() {
        //Build the table
        propertiesTable.add(labelName).left().padRight(10F); propertiesTable.add(textName).padBottom(7f).row()
        propertiesTable.add(labelImage).left().padRight(10F); propertiesTable.add(buttonImage.rotatable()).size(50f).row()
        propertiesTable.add(labelBulletImage).left().padRight(10F); propertiesTable.add(buttonBulletImage.rotatable()).size(50f).row()
        propertiesTable.add(labelDamage).left().padRight(10F); propertiesTable.add(textDamage).row()
        propertiesTable.add(labelAttackSpeed).left().padRight(10F); propertiesTable.add(textAttackSpeed).row()
        propertiesTable.add(labelRange).left().padRight(10F); propertiesTable.add(textRange).row()
        propertiesTable.add(labelTargets).left().padRight(10F); propertiesTable.add(textTargets).row()
        propertiesTable.add(labelSplash).left().padRight(10F); propertiesTable.add(textSplash).row()

        propertiesTable.add(checkAttackAir).left().row()
        propertiesTable.add(checkViewInvisible).left().row()
        propertiesTable.add(checkSlow).left().row()
        tableSlow.add(labelSlowChance).left().padRight(10F); tableSlow.add(textSlowChance).left().row()
        tableSlow.add(labelSlowDuration).left().padRight(10F); tableSlow.add(textSlowDuration).left().row()
        tableSlow.add(labelSlowPercent).left().padRight(10F); tableSlow.add(textSlowPercent).left().row()
        propertiesTable.add(containerSlow).colspan(2).left().row()
        propertiesTable.add(checkStun).left().row()
        tableStun.add(labelStunChance).left().padRight(10F); tableStun.add(textStunChance).left().row()
        tableStun.add(labelStunDuration).left().padRight(10F); tableStun.add(textStunDuration).left().row()
        propertiesTable.add(containerStun).colspan(2).left().row()
        propertiesTable.add(checkCriticalStrike).left().row()
        tableCrit.add(labelCriticalChance).left().padRight(10F); tableCrit.add(textCriticalChance).left().row()
        tableCrit.add(labelCriticalMultiplier).left().padRight(10F); tableCrit.add(textCriticalMultiplier).left().row()
        propertiesTable.add(containerCrit).colspan(2).left().row()
        propertiesTable.addSeparator().colspan(2).expandX().fillX().row()
        // TODO there are no coins or update so this is still todo :(
        propertiesTable.add(soundTable).colspan(2).left().row()
        // propertiesTable.addSeparator().colspan(2).expandX().fillX().row()
        // propertiesTable.add(coinCostTable).colspan(2).left().row()
        // propertiesTable.add(updaterTable).height(200f).width(Value.percentWidth(1f, propertiesTable)).colspan(2).left().row()

        //We must have this tables, else we can't place things at the top
        val tableList = VisTable(true)
        tableList.add(list).fillX().expand().top()
        val tableProp = VisTable(true)
        tableProp.add(propertiesTable).padLeft(17F).fillX().expand().top()

        splitPane = VisSplitPane(tableList, tableProp.scrollable(), false)
        splitPane.setSplitAmount(0.40F)
    }

    override fun newItem() {
        val turret = Turret("Turret ${Random().nextInt(10000)}", Resource())
        Bus.post(turret, BusTopic.CREATED)
        list.selectItem(turret)
        updateUI(turret)
    }

    override fun updateUI(item: Turret) {
        disableProgrammaticEvents()

        textName.text = item.name
        buttonImage.mapObject = item
        buttonBulletImage.mapObject = item.bullet
        textDamage.text = item.damage.toString()
        textRange.text = item.range.toString()
        textAttackSpeed.text = item.attackSpeed.toString()
        textTargets.text = item.numberOfTargets.toString()
        textSplash.text = item.splashRadius.toString()
        textDamage.text = item.damage.toString()
        textDamage.text = item.damage.toString()
        textDamage.text = item.damage.toString()
        textDamage.text = item.damage.toString()

        checkViewInvisible.isChecked = item.viewInvisibles
        checkAttackAir.isChecked = item.attacksAir

        checkStun.isChecked = item.canStun
        containerStun.isCollapsed = !checkStun.isChecked
        textStunDuration.text = item.stunDuration.toString()
        textStunChance.text = item.stunChance.toString()

        checkCriticalStrike.isChecked = item.canCrit
        containerCrit.isCollapsed = !checkCriticalStrike.isChecked
        textCriticalMultiplier.text = item.critMultiplier.toString()
        textCriticalChance.text = item.critChance.toString()

        checkSlow.isChecked = item.canSlow
        containerSlow.isCollapsed = !checkSlow.isChecked
        textSlowPercent.text = item.slowPercent.toString()
        textSlowChance.text = item.slowChance.toString()
        textSlowDuration.text = item.slowDuration.toString()

        soundTable.update(item)

        enableProgrammaticEvents()
    }

}