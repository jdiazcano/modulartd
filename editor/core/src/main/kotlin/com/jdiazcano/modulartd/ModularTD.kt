package com.jdiazcano.modulartd

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.plugins.Plugin
import com.jdiazcano.modulartd.plugins.PluginLoader
import com.jdiazcano.modulartd.ui.MainMenu
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTable
import mu.KLogging
import org.apache.log4j.PropertyConfigurator
import org.apache.log4j.xml.DOMConfigurator
import java.io.File

class ModularTD : ApplicationAdapter() {
    companion object: KLogging()

    private lateinit var stage: Stage
    private val pluginLoader = PluginLoader()
    private lateinit var menu: MainMenu

    override fun create() {
        PropertyConfigurator.configure(Gdx.files.internal("log4j.properties").file().toURI().toURL())
        VisUI.load()

        menu = MainMenu()

        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage

        pluginLoader.loadBundledPlugins()
        pluginLoader.loadExternalPlugins()

        val root = VisTable()
        root.setFillParent(true)
        stage.addActor(root)

        root.add(menu.table).fillX().expandX().row()
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
        VisUI.dispose()
        stage.dispose()
    }
}