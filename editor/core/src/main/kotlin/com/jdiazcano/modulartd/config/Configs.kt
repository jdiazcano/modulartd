package com.jdiazcano.modulartd.config

import org.cfg4j.provider.ConfigurationProvider
import org.cfg4j.provider.ConfigurationProviderBuilder
import org.cfg4j.source.classpath.ClasspathConfigurationSource
import org.cfg4j.source.compose.MergeConfigurationSource
import org.cfg4j.source.context.environment.ImmutableEnvironment
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider
import org.cfg4j.source.files.FilesConfigurationSource
import org.cfg4j.source.reload.strategy.PeriodicalReloadStrategy
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

object Configs {
    private val classPathSource = ClasspathConfigurationSource(ConfigFilesProvider { arrayListOf(Paths.get("app.json")) })
    private val overrideSource = FilesConfigurationSource(ConfigFilesProvider { arrayListOf(Paths.get("override.json")) })
    private val provider: ConfigurationProvider = ConfigurationProviderBuilder()
            .withEnvironment(ImmutableEnvironment("."))
            .withConfigurationSource(MergeConfigurationSource(classPathSource, overrideSource))
            .withReloadStrategy(PeriodicalReloadStrategy(60, TimeUnit.SECONDS))
            .build()

    /**
     * Editor config, this config will be mostly GUI and tower defense specific stuff.
     */
    fun editor() : EditorConfig = provider.bind("editor", EditorConfig::class.java)

    fun <T> classPath(resourceName: String, clazz: Class<T>, prefix: String = "") : T {
        val source = ClasspathConfigurationSource(ConfigFilesProvider { arrayListOf(Paths.get(resourceName)) })
        val provider: ConfigurationProvider = ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .withReloadStrategy(PeriodicalReloadStrategy(5, TimeUnit.SECONDS))
                .build()
        return provider.bind(prefix, clazz)
    }

    fun <T> file(fileName: String, clazz: Class<T>, prefix: String = "") : T {
        val source = FilesConfigurationSource(ConfigFilesProvider { arrayListOf(Paths.get(fileName)) })
        val provider: ConfigurationProvider = ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .withReloadStrategy(PeriodicalReloadStrategy(60, TimeUnit.SECONDS))
                .build()
        return provider.bind(prefix, clazz)
    }
}

