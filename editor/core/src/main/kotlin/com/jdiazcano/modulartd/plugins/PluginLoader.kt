package com.jdiazcano.modulartd.plugins

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.JsonReader
import com.jdiazcano.modulartd.ActionManager
import com.jdiazcano.modulartd.config.Configs
import com.jdiazcano.modulartd.config.EditorConfig
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.utils.readUtf8
import com.jdiazcano.modulartd.utils.toURL
import mu.KLogging
import java.util.jar.JarFile
import java.util.jar.Manifest

/**
 * Created by javierdiaz on 19/11/2016.
 */
class PluginLoader {
    companion object: KLogging()

    private val plugins: MutableList<Plugin> = mutableListOf()
    private val listeners: MutableList<(Plugin) -> Unit> = mutableListOf()

    private val config : EditorConfig = Configs.editor()

    fun loadPlugins() {
        logger.debug { "Using plugins folder: ${config.pluginsFolder()} and file: ${config.pluginsConfigFile()}" }
        val pluginsFolder = Gdx.files.internal(config.pluginsFolder())
        JsonReader().parse(pluginsFolder.child(config.pluginsConfigFile()).readUtf8()).forEach {
            val pluginFileName = it["file"].asString()
            logger.debug { "Loading plugin: $pluginFileName" }
            val plugin = loadPlugin(pluginsFolder.child(pluginFileName))
            plugin.javaClass.declaredMethods.forEach { method ->
                if (method.isAnnotationPresent(RegisterAction::class.java)) {
                    ActionManager.registerAction(method.invoke(plugin) as Action)
                }
            }
            plugin.onLoad()
            registerPlugin(plugin)
            logger.debug { "Plugin loaded: $pluginFileName" }
        }
    }

    private fun registerPlugin(plugin: Plugin) {
        plugins.add(plugin)
        listeners.forEach { it.invoke(plugin) }
    }

    fun listen(listener: (Plugin) -> Unit) = listeners.add(listener)

    fun loadPlugin(file: FileHandle) : Plugin {
        val pluginJar = JarFile(file.file())
        val mainClass = Manifest(pluginJar.getInputStream(pluginJar.getJarEntry("META-INF/MANIFEST.MF"))).mainAttributes.getValue("Main-Class")
        val loader = PluginClassLoader(arrayOf(file.toURL()))
        val pluginClass = loader.loadPlugin(mainClass)
        return pluginClass.newInstance()
    }
}