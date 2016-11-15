package com.jdiazcano.modulartd.beans

import com.jdiazcano.modulartd.config.Configs

/**
 * Map class, this will define the object of a map, the whole structure that will contain all the properties, units,
 * turrets and everything regarding a map
 */
class Map(
        var name: String = "",
        var version: String = "v0.1",
        var description: String = "",
        var author: String = "",
        var authorNotes: String = "",

        var gridWidth: Int = Configs.editor().gridWidth(),
        var gridHeight: Int = Configs.editor().gridHeight(),
        var tileWidth: Int = Configs.editor().tileWidth(),
        var tileHeight: Int = Configs.editor().tileHeight(),

        var timeBetweenLevels: Float = Configs.editor().timeBetweenLevels(),
        var interestRatio: Float = Configs.editor().interestRatio(),
        var turretSellProfit: Float = Configs.editor().turretSellProfit(),
        // TODO Need a way to do this better in order to be able to script the end of the game, maybe the player will
        // TODO want to end the game if there are more than N units of one kind, not all of them or something like that!
        // var loseCondition: LoseCondition = Configs.editor().loseCondition()

        // TODO same as above!
        // var endAction: EndAction = Configs.editor().endAction()
        var unitCount: Int = Configs.editor().unitCount(),

        override var script: String = ""): Scriptable {

    var layers: Array<Layer> = arrayOf()
    var turrets: Array<Turret> = arrayOf()
    var units: Array<Unit> = arrayOf()
    var waves: Array<Wave> = arrayOf()
    var tiles: Array<Tile> = arrayOf()

}