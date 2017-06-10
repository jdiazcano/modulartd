package com.jdiazcano.modulartd.plugins.bundled

import com.badlogic.gdx.Input
import com.badlogic.gdx.files.FileHandle
import com.github.salomonbrys.kodein.instance
import com.google.gson.Gson
import com.jdiazcano.modulartd.beans.Game
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.config.Configs
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.keys.Modifiers
import com.jdiazcano.modulartd.keys.ShortCut
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Menus
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.ui.StageWrapper
import com.jdiazcano.modulartd.utils.createErrorDialog
import com.jdiazcano.modulartd.utils.filewatcher.AssetDirectoryWatcher
import com.jdiazcano.modulartd.utils.setSingleFileListener
import com.jdiazcano.modulartd.utils.showErrorDialog
import com.jdiazcano.modulartd.utils.translate
import com.kotcrab.vis.ui.widget.file.FileChooser

/**
 * Creates a new project (TD)
 */
class OpenPlugin : Plugin {

    private var currentWatcher: AssetDirectoryWatcher = kodein.instance()

    init {
        Bus.register<LoadMapEvent>(LoadMapEvent::class.java, BusTopic.MAP_LOAD) { (file, reloadMap) ->
            val game: Game = kodein.instance()
            val editor = Configs.editor

            val title = "${file.path()} - ${Configs.editor.baseTitle()}"
            game.title = title

            game.gameFolder = file

            if (reloadMap) {
                val mapFile = file.child(editor.gameConfigFolder()).child(editor.gameFileName())
                val loadedMap = Gson().fromJson(mapFile.reader(), Map::class.java)
                val map: Map = kodein.instance()
                Bus.post(Unit, BusTopic.RESET)
                map.apply {
                    resources.clear()
                    resources.addAll(loadedMap.resources)
                    Bus.post(map.resources, BusTopic.RESOURCES_RELOAD)

                    // TODO Do this with reflection so I don't have to add a new field here when a new field is added
                    name = loadedMap.name
                    version = loadedMap.version
                    description = loadedMap.description
                    author = loadedMap.author
                    authorNotes = loadedMap.authorNotes
                    gridWidth = loadedMap.gridWidth
                    gridHeight = loadedMap.gridHeight
                    tileWidth = loadedMap.tileWidth
                    tileHeight = loadedMap.tileHeight
                    timeBetweenLevels = loadedMap.timeBetweenLevels
                    interestRatio = loadedMap.interestRatio
                    turretSellProfit = loadedMap.turretSellProfit
                    unitCount = loadedMap.unitCount
                    loadedMap.units.forEach { Bus.post(it, BusTopic.CREATED) }
                    loadedMap.layers.forEach { Bus.post(it, BusTopic.CREATED) }
                    loadedMap.turrets.forEach { Bus.post(it, BusTopic.CREATED) }
                    loadedMap.waves.forEach { Bus.post(it, BusTopic.CREATED) }
                    loadedMap.tiles.forEach { Bus.post(it, BusTopic.CREATED) }
                    loadedMap.coins.forEach { Bus.post(it, BusTopic.CREATED) }
                    script = loadedMap.script
                }
                Bus.post(map, BusTopic.MAP_LOAD)
            }

            currentWatcher.stopWatching()
            currentWatcher = kodein.instance()
            currentWatcher.startWatching()
        }
    }

    override fun getName() = "Open plugin"

    override fun getVersion() = 1

    override fun getMinimumCompatibleVersion() = 1

    override fun getPrefix() = "open"

    override fun onLoad() {}

    override fun onUnload() {}

    @RegisterAction(Menus.FILE)
    fun open() = OpenAction()
}

class OpenAction : Action("file.open", translate("file.open"), ShortCut(Input.Keys.O, Modifiers(control = true))) {
    override fun perform(stage: StageWrapper) {
        val errorDialog = createErrorDialog("Failed to open", "You can only select one folder when opening or creating a project")
        val chooser = FileChooser(translate("select.project"), FileChooser.Mode.OPEN)
        chooser.setWatchingFilesEnabled(true)
        chooser.selectionMode = FileChooser.SelectionMode.DIRECTORIES
        chooser.setSingleFileListener(errorDialog) { file ->
            if (file.child(Configs.editor.gameConfigFolder()).exists()) {
                Bus.post(LoadMapEvent(file, true), BusTopic.MAP_LOAD)
            } else {
                showErrorDialog("Failed to open", "This is not a game folder")
            }

        }

        Bus.post(chooser.fadeIn(), BusTopic.NEW_DIALOG)
    }
}

data class LoadMapEvent(val file: FileHandle, val reloadMap: Boolean)