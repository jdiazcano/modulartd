package com.jdiazcano.modulartd.config

import com.jdiazcano.konfig.hocon.HoconConfigLoader
import com.jdiazcano.konfig.providers.DefaultConfigProvider
import com.jdiazcano.konfig.providers.OverrideConfigProvider
import com.jdiazcano.konfig.providers.bind
import com.jdiazcano.konfig.reloadstrategies.FileChangeReloadStrategy
import com.jdiazcano.rxpreferences.RxDesktopPreferences
import java.io.File
import java.util.prefs.Preferences

object Configs {
    private val classPathSource = DefaultConfigProvider(
            HoconConfigLoader(javaClass.getResource("/app.json"))
    )
    private val overrideSource = DefaultConfigProvider(
            HoconConfigLoader(File("override.hocon")),
            FileChangeReloadStrategy(File("override.hocon"))
    )
    private val provider = OverrideConfigProvider(classPathSource, overrideSource)

    private val preferences = RxDesktopPreferences(Preferences.userRoot().node(editor().preferencesKey()))

    /**
     * Editor config, this config will be mostly GUI and tower defense specific stuff.
     */
    fun editor(): EditorConfig = provider.bind<EditorConfig>("editor")
    fun preferences() = preferences
}

