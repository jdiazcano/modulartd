package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx

/**
 * Simple utils to know which operative system (or mobile) we are in, right now this app is only thought for Desktop but
 * who knows in the future if tablets will be supported!
 */
object OSUtils {
    fun windows() : Boolean = System.getProperty("os.name").toLowerCase().contains("win")
    fun mac() : Boolean = System.getProperty("os.name").toLowerCase().contains("mac")
    fun unix() : Boolean = System.getProperty("os.name").toLowerCase().contains(Regex("(nix|nux|aix)"))
    fun ios() : Boolean = Gdx.app.type == Application.ApplicationType.iOS
    fun android() : Boolean = Gdx.app.type == Application.ApplicationType.Android
}