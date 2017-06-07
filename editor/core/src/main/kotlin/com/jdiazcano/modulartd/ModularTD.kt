package com.jdiazcano.modulartd

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.config.Configs
import com.jdiazcano.modulartd.injections.kodein
import com.jdiazcano.modulartd.keys.Priorities
import com.jdiazcano.modulartd.keys.PriorityInputMultiplexer
import com.jdiazcano.modulartd.keys.PriorityProcessor
import com.jdiazcano.modulartd.plugins.PluginLoader
import com.jdiazcano.modulartd.ui.MainMenu
import com.jdiazcano.modulartd.ui.PopupMenuLocation
import com.jdiazcano.modulartd.ui.widgets.OpenProjectDialog
import com.jdiazcano.modulartd.utils.filewatcher.AssetDirectoryWatcher
import com.jdiazcano.modulartd.utils.toURL
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.file.FileChooser
import mu.KLogging
import org.apache.log4j.PropertyConfigurator

class ModularTD : ApplicationAdapter() {
    companion object: KLogging()

    private lateinit var stage: Stage
    private val pluginLoader = PluginLoader()
    private lateinit var menu: MainMenu
    private lateinit var screen: MainScreenUI

    override fun create() {
        PropertyConfigurator.configure(Gdx.files.internal("log4j.properties").toURL())
        FileChooser.setDefaultPrefsName(Configs.editor.preferencesKey())
        VisUI.load()
        registerBusTopics()
        Gdx.graphics.setTitle(Configs.editor.baseTitle())
        kodein.instance<AssetDirectoryWatcher>().startWatching()
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = PriorityInputMultiplexer(stage)
        Bus.post(PriorityProcessor(stage, Priorities.STAGE), BusTopic.PROCESSOR_REGISTERED)

        val root = VisTable()

        menu = MainMenu()
        screen = MainScreenUI(root)

        pluginLoader.loadBundledPlugins()
        pluginLoader.loadExternalPlugins()
        pluginLoader.loadAfterBundledPlugins()

        root.setFillParent(true)
        stage.addActor(root)

        root.add(menu.table).expandX().fillX().padBottom(5F).row()
        root.add(screen).expand().fill().row()
        root.add(kodein.instance<Label>("globalMessageLabel")).expandX().left().row()

        OpenProjectDialog.select()
    }

    private fun registerBusTopics() {
        // This will handle the dialogs that will be posted in the stage
        Bus.register<Actor>(Actor::class.java, BusTopic.NEW_DIALOG) {
            stage.addActor(it)
        }

        Bus.register<PopupMenuLocation>(PopupMenuLocation::class.java, BusTopic.POPUP_MENU) { (menu, x, y) ->
            menu.showMenu(stage, x, y)
            menu.toFront()
        }
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Math.min(Gdx.graphics.deltaTime, 1 / 30f))
        stage.draw()
    }

    override fun dispose() {
        kodein.instance<AssetDirectoryWatcher>().stopWatching()
        kodein.instance<ResourceManager>().dispose()
        VisUI.dispose()
        stage.dispose()
    }
}