package com.jdiazcano.modulartd.config

import com.jdiazcano.konfig.loaders.JsonConfigLoader
import com.jdiazcano.konfig.providers.DefaultConfigProvider
import com.jdiazcano.konfig.providers.OverrideConfigProvider
import com.jdiazcano.konfig.providers.bind
import com.jdiazcano.konfig.reloadstrategies.FileChangeReloadStrategy
import com.jdiazcano.rxpreferences.RxDesktopPreferences
import java.io.File
import java.util.*
import java.util.prefs.Preferences

object Configs {
    private val classPathSource = DefaultConfigProvider(
            JsonConfigLoader(javaClass.getResource("/settings.json"))
    )
    private val overrideSource = DefaultConfigProvider(
            JsonConfigLoader(File("override.json").toURI().toURL()),
            FileChangeReloadStrategy(File("override.json"))
    )
    private val provider = OverrideConfigProvider(classPathSource, overrideSource)

    /**
     * Editor config, this config will be mostly GUI and tower defense specific stuff. This is a static default value
     * store, nothing else.
     */
    val editor = provider.bind<EditorConfig>("editor")

    val preferences = RxDesktopPreferences(Preferences.userRoot().node(editor.preferencesKey()))
}

// TODO rethink if the override is needed, all strings should be there and if something fails is because it should fail
object Translations {
    private val translationsProvider = DefaultConfigProvider(JsonConfigLoader(javaClass.getResource("/strings/strings.json")))
    private val localedTranslationsProvider = DefaultConfigProvider(
            JsonConfigLoader(javaClass.getResource("/strings/strings_${Configs.preferences.getString("editor.language", Locale.getDefault().toString()).get()}.json"))
    )

    private val overriddenTranslationsProvider = OverrideConfigProvider(localedTranslationsProvider, translationsProvider)

    fun of(key: String, default: String? = null): String {
        return overriddenTranslationsProvider.getProperty(key, String::class.java, default)
    }
}
