package com.jdiazcano.modulartd.ui.widgets

import com.jdiazcano.modulartd.beans.Coin
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.ui.AnimatedActor
import com.jdiazcano.modulartd.utils.changeListener
import com.jdiazcano.modulartd.utils.clickListener
import com.jdiazcano.modulartd.utils.input
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisValidatableTextField

/**
 * This will let you edit (add, modify or delete) all the coins that you have in your map
 */
class CoinEditor(val coins: MutableList<Coin>): VisTable(true) {
    private val list = CoinListView(coins)
    private val addCoin = VisTextButton(translate("coin.add")) // TODO translate
    private val renameCoin = VisTextButton(translate("coin.rename"))


    init {
        Bus.register<Coin>(Coin::class.java, BusTopic.DELETED) { deletedCoin ->
            list.removeItem(deletedCoin)
            list.notifyDataSetChanged()
        }

        Bus.register<Coin>(Coin::class.java, BusTopic.CREATED) { addedCoin ->
            list.addItem(addedCoin)
            list.notifyDataSetChanged()
        }

        // TODO something to add a coin, it should ask only for the name or also the icon...
        buildTable()
        setUpListeners()
    }

    private fun setUpListeners() {
        addCoin.changeListener { _, _ ->
            input("Coin name", "Coin name") {
                Bus.post(Coin(it), BusTopic.CREATED)
            }
        }

        renameCoin.changeListener { _, _ ->
            input("Coin name", "Coin name") {
                list.selectedItem.name = it
                list.notifyDataSetChanged()
            }
        }
    }

    private fun buildTable() {
        add(list).colspan(999).expand().fill().row()
        add(addCoin).expandX().center()
        add(renameCoin).expandX().center()
    }

}

class CoinListView(objects: MutableList<Coin>): TableList<Coin, CoinEditorRow>(objects) {

    override fun getView(position: Int, lastView: CoinEditorRow?): CoinEditorRow {
        val coin = getItem(position)
        val view = if (lastView == null) {
            val objectView = CoinEditorRow(coin)
            objectView.clickListener { _, _, _ ->
                Bus.post(getItem(position), BusTopic.SELECTED)
            }
            objectView
        } else {
            lastView.name.setText(coin.name)
            lastView.icon.swapResource(coin.resource)
            lastView
        }

        return view
    }

}

/**
 * Defines how the row of a coin while editing it will look like
 *
 * Will look like:
 *
 * ICON: Name  StartingValue    (x)
 */
class CoinEditorRow(val coin: Coin): VisTable() {
    val name = VisLabel(coin.name)
    val icon = AnimatedActor(coin.resource)
    val startingValue = VisValidatableTextField("0")
    private val delete = VisTextButton("x")

    init {
        buildTable()
        setUpListeners()
        setUpValidatableForm()


    }

    private fun setUpValidatableForm() {
        startingValue.addValidator { input ->
            input.toIntOrNull() != null
        }
    }

    private fun setUpListeners() {
        delete.changeListener { _, _ ->
            Bus.post(coin, BusTopic.DELETED)
        }

        name.clickListener { _, _, _ ->
            Bus.post(coin, BusTopic.SELECTED)
        }
    }

    private fun buildTable() {
        name.setWrap(true)

        add(icon).size(24F)
        add(name).expandX().left().maxWidth(200F).padRight(10F)
        add(startingValue).width(50F).padRight(3F)
        add(delete)
    }
}