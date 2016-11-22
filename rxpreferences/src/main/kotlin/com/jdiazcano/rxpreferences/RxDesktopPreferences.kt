package com.jdiazcano.rxpreferences

import rx.Observable
import rx.subscriptions.Subscriptions
import java.util.prefs.PreferenceChangeListener
import java.util.prefs.Preferences

class RxDesktopPreferences(
        val preferences: Preferences
) {
    companion object {
        val DEFAULT_FLOAT = 0f
        val DEFAULT_INTEGER = 0
        val DEFAULT_BOOLEAN = false
        val DEFAULT_LONG = 0L
    }

    val keyChanges: Observable<String>

    init {
        keyChanges = Observable.create(Observable.OnSubscribe<String> { subscriber ->
            val listener = PreferenceChangeListener { evt ->
                if (evt != null) {
                    subscriber.onNext(evt.key)
                    println(evt.key)
                }
            }

            preferences.addPreferenceChangeListener(listener)

            subscriber.add(Subscriptions.create {
                preferences.removePreferenceChangeListener(listener)
            })
        }).share()
    }

    fun getBoolean(key: String, default: Boolean = DEFAULT_BOOLEAN): Preference<Boolean> {
        return Preference(preferences, key, default, BooleanAdapter, keyChanges)
    }

    fun getInteger(key: String, default: Int = DEFAULT_INTEGER): Preference<Int> {
        return Preference(preferences, key, default, IntegerAdapter, keyChanges)
    }

    fun getLong(key: String, default: Long = DEFAULT_LONG): Preference<Long> {
        return Preference(preferences, key, default, LongAdapter, keyChanges)
    }

    fun getFloat(key: String, default: Float = DEFAULT_FLOAT): Preference<Float> {
        return Preference(preferences, key, default, FloatAdapter, keyChanges)
    }

    fun getString(key: String, default: String = ""): Preference<String> {
        return Preference(preferences, key, default, StringAdapter, keyChanges)
    }

}
