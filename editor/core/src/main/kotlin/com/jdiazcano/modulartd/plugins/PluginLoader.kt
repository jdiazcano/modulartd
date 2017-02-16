package com.jdiazcano.modulartd.plugins

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.JsonReader
import com.jdiazcano.modulartd.ActionManager
import com.jdiazcano.modulartd.ParentedAction
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.config.Configs
import com.jdiazcano.modulartd.config.EditorConfig
import com.jdiazcano.modulartd.plugins.actions.Action
import com.jdiazcano.modulartd.plugins.actions.Preferences
import com.jdiazcano.modulartd.plugins.actions.RegisterAction
import com.jdiazcano.modulartd.plugins.bundled.ExitPlugin
import com.jdiazcano.modulartd.plugins.bundled.PreferencesPlugin
import com.jdiazcano.modulartd.utils.readUtf8
import com.jdiazcano.modulartd.utils.toURL
import mu.KLogging
import java.util.jar.JarFile
import java.util.jar.Manifest
import kotlin.system.measureTimeMillis

/**
 * Loads the plugins and sends them to the bus to be picked by the listeners
 */
class PluginLoader {
    companion object: KLogging()

    private val plugins: MutableList<Plugin> = mutableListOf()

    private val config : EditorConfig = Configs.editor()

    init {
        Bus.register(Plugin::class.java, BusTopic.PLUGIN_REGISTERED) { plugin ->
            logger.debug { "Plugin loaded: ${plugin.getName()}." }
        }
    }

    fun loadExternalPlugins() {
        logger.debug { "Using plugins folder: '${config.pluginsFolder()}' and file: '${config.pluginsConfigFile()}'" }
        val pluginsFolder = Gdx.files.internal(config.pluginsFolder())
        JsonReader().parse(pluginsFolder.child(config.pluginsConfigFile()).readUtf8()).forEach {
            val pluginFileName = it["file"].asString()
            logger.debug { "Loading external plugin: '$pluginFileName'" }
            val plugin = loadPlugin(pluginsFolder.child(pluginFileName))
            val millis = measureTimeMillis { registerPlugin(plugin) }
            logger.debug { "External plugin loaded: '$pluginFileName', took $millis milliseconds" }
        }
    }

    private fun registerPlugin(plugin: Plugin) {
        plugin.javaClass.declaredMethods.forEach { method ->
            method.annotations.forEach { annotation ->
                when (annotation) {
                    is RegisterAction -> {
                        val action = method.invoke(plugin) as Action
                        ActionManager.registerAction(action)
                        Bus.post(ParentedAction(action, annotation.id), BusTopic.ACTION_REGISTERED)
                    }
                    is Preferences -> {
                        Bus.post(method.invoke(plugin), BusTopic.PREFERENCES_REGISTERED)
                    }
                }
            }
        }
        plugin.onLoad()
        plugins.add(plugin)
        Bus.post(plugin, BusTopic.PLUGIN_REGISTERED)
    }

    fun loadPlugin(file: FileHandle) : Plugin {
        val pluginJar = JarFile(file.file())
        val mainClass = Manifest(pluginJar.getInputStream(pluginJar.getJarEntry("META-INF/MANIFEST.MF"))).mainAttributes.getValue("Main-Class")
        val loader = PluginClassLoader(arrayOf(file.toURL()))
        val pluginClass = loader.loadPlugin(mainClass)
        return pluginClass.newInstance()
    }

    fun loadBundledPlugins() {
        // File
        registerPlugin(ExitPlugin())

        // Edit
        registerPlugin(PreferencesPlugin())

        // Help
    }
}