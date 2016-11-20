package com.jdiazcano.modulartd

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.jdiazcano.modulartd.plugins.PluginLoader
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisWindow

class ModularTD : ApplicationAdapter() {
    private lateinit var stage: Stage
    private val pluginLoader = PluginLoader()

    override fun create() {
        VisUI.load()

        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage

        pluginLoader.listen { plugin -> println("This plugin has been loaded: ${plugin.getName()}!!") }
        pluginLoader.loadPlugins()

        val root = VisTable()
        root.setFillParent(true)
        stage.addActor(root)

        val textButton = VisTextButton("click me!")
        textButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                textButton.setText("clicked")
                Dialogs.showOKDialog(stage, "message", "good job!")
            }
        })

        val window = VisWindow("example window")
        window.add("this is a simple VisUI window").padTop(5f).row()
        window.add(textButton).pad(10f)
        window.pack()
        window.centerWindow()
        stage.addActor(window.fadeIn())
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