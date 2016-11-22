package com.jdiazcano.rxpreferences

import rx.Observable
import java.util.prefs.Preferences

class Preference<T>(
        val preferences: Preferences,
        val key: String,
        val defaultValue: T,
        val adapter: Adapter<T>,
        keyChanges: Observable<String>
) {

    interface Adapter<T> {
        fun get(key: String, preferences: Preferences): T

        fun set(key: String, value: T, preferences: Preferences)
    }

    private val values: Observable<T>

    init {
        values = keyChanges.filter { keyChanged ->
            key.equals(keyChanged)
        }.startWith("<init>").onBackpressureBuffer().map {
            get()
        }
    }

    fun get(): T {
        if (preferences.get(key, null) == null) {
            return defaultValue
        } else {
            return adapter.get(key, preferences)
        }
    }

    fun set(value: T?) {
        if (value == null) {
            preferences.remove(key)
        } else {
            adapter.set(key, value, preferences)
        }
        preferences.flush()
    }

    fun delete() = set(null)

    fun isSet() = preferences.get(key, null) != null

    fun asObservable() = values

}