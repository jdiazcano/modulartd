package com.jdiazcano.modulartd.config

import com.jdiazcano.rxpreferences.RxDesktopPreferences
import java.util.prefs.Preferences

fun main(args: Array<String>) {
    val pref1 = RxDesktopPreferences(Preferences.userRoot().node("com.jdiaz.test"))
    val preference1 = pref1.getString("test")
    preference1.asObservable().subscribe { value ->
        println("New value: $value")
    }
    val pref2 = RxDesktopPreferences(Preferences.userRoot().node("com.jdiaz.test"))
    val preference = pref2.getString("test")
    preference.set("testerino")
}