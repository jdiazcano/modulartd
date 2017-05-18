package com.jdiazcano.modulartd.tabs

import com.badlogic.gdx.scenes.scene2d.Actor
import com.jdiazcano.modulartd.config.Translations
import com.kotcrab.vis.ui.widget.*

class MobTab: BaseTab(Translations.of("tabs.mobs", "Mobs")) {
    private lateinit var splitPane: VisSplitPane
    private val propertiesTable = VisTable(true)

    private val labelName = VisLabel(Translations.of("name", "Name"))
    private val textName = VisValidatableTextField()
    private val labelImage = VisLabel(Translations.of("image", "Image"))
    //private lateinit var buttonImage: AnimatedButton
    private val labelArmor = VisLabel(Translations.of("armor", "Armor"))
    private val textArmor = VisValidatableTextField()
    private val labelHitpoints = VisLabel(Translations.of("hitpoints", "Hit points"))
    private val textHitpoints = VisValidatableTextField()
    private val labelHPregen = VisLabel(Translations.of("hpregen", "HP regen"))
    private val textHPregen = VisValidatableTextField()
    private val labelMovementSpeed = VisLabel(Translations.of("speed", "Speed"))
    private val textMovementSpeed = VisValidatableTextField()

    /* CheckBoxes */
    private val checkAir = VisCheckBox(Translations.of("air", "Air"))
    private val checkInvisible = VisCheckBox(Translations.of("invisible", "Invisible"))
    private val checkAntiSlow = VisCheckBox(Translations.of("antislow", "Anti slow"))
    private val checkAntiStun = VisCheckBox(Translations.of("antistun", "Anti stun"))

    //private var soundTable: SoundTable<UnitSound>? = null
    //private var coinDropTable: CoinQuantifierTable? = null

    init {
        buildTable()
    }

    private fun buildTable() {
        setUpValidableForm()
        placeComponents()

        content.add(splitPane).expand().fill()
    }

    private fun placeComponents() {
        //Build the table
        propertiesTable.add(labelName).left().padRight(10F)
        propertiesTable.add(textName).padBottom(7F).row()
        propertiesTable.add(labelMovementSpeed).left().padRight(10F)
        propertiesTable.add(textMovementSpeed).row()
        propertiesTable.add(labelImage).left().padRight(10F).row()
        //propertiesTable.add(ImagePickerWindow(editor, buttonImage)).size(50).row()
        propertiesTable.add(labelHitpoints).left().padRight(10F)
        propertiesTable.add(textHitpoints).row()
        propertiesTable.add(labelArmor).left().padRight(10F)
        propertiesTable.add(textArmor).row()
        propertiesTable.add(labelHPregen).left().padRight(10F)
        propertiesTable.add(textHPregen).row()

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
        tableList.add(VisTable()).fillX().expand().top()
        val tableProp = VisTable(true)
        tableProp.add<Actor>(propertiesTable).padLeft(17f).fillX().expand().top()

        splitPane = VisSplitPane(tableList, tableProp, false)
        splitPane.setSplitAmount(0.40f)
    }

    private fun setUpValidableForm() {
        validator.notEmpty(textName, "Name can't be empty")
        validator.valueGreaterThan(textArmor, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textHitpoints, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textHPregen, "It needs to be a positive value", 0F, true)
        validator.valueGreaterThan(textMovementSpeed, "It needs to be a positive value", 0F, true)
    }

    private fun initializeComponents() {
        //list = UnitList(InfiniteEditor.game.getUnits(), this)

        //buttonImage = AnimatedButton(AnimatedActor(null))
        //buttonImage.addListener(object : ClickListener() {
        //    fun clicked(event: InputEvent, x: Float, y: Float) {
        //        picker.addPickListener({ resource ->
        //            buttonImage.setResource(resource)
        //            getList().notifyDataSetChanged()
        //        })
        //
        //        picker.pickResource(ResourceType.IMAGE)
        //    }
        //})

    }

}