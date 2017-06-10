package com.jdiazcano.modulartd.tabs

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.beans.*
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.ui.widgets.AnimatedButton
import com.jdiazcano.modulartd.ui.widgets.lists.LayerList
import com.jdiazcano.modulartd.ui.widgets.lists.MapObjectList
import com.jdiazcano.modulartd.ui.widgets.pickResource
import com.jdiazcano.modulartd.ui.widgets.rotatable
import com.jdiazcano.modulartd.utils.clickListener
import com.jdiazcano.modulartd.utils.input
import com.jdiazcano.modulartd.utils.sneakyChange
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.*
import java.util.*

class TileTab: BaseTab<Tile>(translate("tabs.tiles"), true) {

    private lateinit var splitPane: VisSplitPane
    private val propertiesTable = VisTable(true)
    private val list = MapObjectList(kodein.instance<Map>().tiles, Tile::class.java)

    private val tableLayers = VisTable(true)
    private lateinit var layersSplitPane: VisSplitPane
    private val layerList = LayerList(kodein.instance<Map>().layers)

    private val labelName = VisLabel(translate("name"))
    private val textName = VisValidatableTextField()
    private val labelImage =  VisLabel(translate("image"))
    private val buttonImage = AnimatedButton()
    private val checkBuildable = VisCheckBox(translate("buildable"))

    init {
        Bus.register<Tile>(Tile::class.java, BusTopic.SELECTED) {
            updateUI(it)
        }

        buildTable()
    }

    private fun buildTable() {
        setUpValidableForm()
        buildTableLayers() // The layers table, this will be shown inside the TileTab and new layers will be created here
        placeComponents()
        addUpdateListeners()
        addTextFieldsForEvents()

        content.add(layersSplitPane).expand().fill()
    }

    private fun addTextFieldsForEvents() {
        textFields += textName
    }

    private fun buildTableLayers() {
        tableLayers.add(translate("layers")).colspan(3).expandX().center().row()
        tableLayers.add(layerList).expand().fill().colspan(3).row()

        val add = VisTextButton(translate("layer.add"))
        add.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                input(translate("layer.name"), translate("layer.name")) {
                    Bus.post(Layer(it), BusTopic.CREATED)
                }

            }
        })
        tableLayers.add(add)

        val rename = VisTextButton(translate("renameLayer"))
        rename.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (layerList.hasSelection()) {
                    input(translate("layer.name"), translate("layer.name")) {
                        layerList.selectedItem.name = it
                        layerList.notifyDataSetChanged()
                    }
                }

            }
        })
        tableLayers.add(rename)

        val remove = VisTextButton(translate("removeLayer"))
        remove.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                layerList.removeItem(layerList.selectedIndex)
            }
        })
        tableLayers.add(remove)
    }

    private fun addUpdateListeners() {
        textName.sneakyChange {
            list.selectedItem.name = textName.text
            list.invalidateSelected()
            game.dirty = true
        }

        buttonImage.clickListener { _, _, _ ->
            pickResource(translate("pick"), ResourceType.IMAGE) {
                buttonImage.resource = it
                list.selectedItem.resource = it
                list.notifyDataSetChanged()
                game.dirty = true
            }
        }
    }

    private fun placeComponents() {
        //Build the table
        propertiesTable.add(labelName).left().padRight(10F)
        propertiesTable.add(textName).padBottom(7F).row()
        propertiesTable.add(labelImage).left().padRight(10F)
        propertiesTable.add(buttonImage.rotatable()).size(50F).row()
        propertiesTable.add(checkBuildable).colspan(2).left().row()

        //We must have this tables, else we can't place things at the top
        val tableList = VisTable(true)
        tableList.add(list).fillX().expand().top()
        val tableProp = VisTable(true)
        tableProp.add(propertiesTable).padLeft(17F).padTop(5F).fillX().expand().top()

        splitPane = VisSplitPane(tableList, tableProp, false)
        splitPane.setSplitAmount(0.40F)
        tableLayers.padBottom(17F)
        layersSplitPane = VisSplitPane(tableLayers, splitPane, true)
        layersSplitPane.setSplitAmount(0.30F)
    }

    private fun setUpValidableForm() {
        validator.notEmpty(textName, "Name can't be empty")
    }

    override fun newItem() {
        val tile = Tile("Tile ${Random().nextInt(10000)}", Resource())
        Bus.post(tile, BusTopic.CREATED)
        list.selectItem(tile)
        updateUI(tile)
    }

    override fun updateUI(item: Tile) {
        disableProgrammaticEvents()

        textName.text = item.name
        checkBuildable.isChecked = item.buildable
        buttonImage.mapObject = item

        enableProgrammaticEvents()
    }

}