package com.jdiazcano.rxpreferences

import java.util.prefs.Preferences

object BooleanAdapter : Preference.Adapter<Boolean> {
    override fun get(key: String, preferences: Preferences): Boolean {
        return preferences.getBoolean(key, false)
    }

    override fun set(key: String, value: Boolean, preferences: Preferences) {
        preferences.putBoolean(key, value)
    }
}

object FloatAdapter : Preference.Adapter<Float> {
    override fun get(key: String, preferences: Preferences): Float {
        return preferences.getFloat(key, 0F)
    }

    override fun set(key: String, value: Float, preferences: Preferences) {
        preferences.putFloat(key, value)
    }
}

object IntegerAdapter : Preference.Adapter<Int> {
    override fun get(key: String, preferences: Preferences): Int {
        return preferences.getInt(key, 0)
    }

    override fun set(key: String, value: Int, preferences: Preferences) {
        preferences.putInt(key, value)
    }
}

object LongAdapter : Preference.Adapter<Long> {
    override fun get(key: String, preferences: Preferences): Long {
        return preferences.getLong(key, 0L)
    }

    override fun set(key: String, value: Long, preferences: Preferences) {
        preferences.putLong(key, value)
    }
}

object StringAdapter : Preference.Adapter<String> {
    override fun get(key: String, preferences: Preferences): String {
        return preferences.get(key, "")
    }

    override fun set(key: String, value: String, preferences: Preferences) {
        preferences.put(key, value)
    }
}

class EnumAdapter<T : Enum<T>>(val enumClass: Class<T>) : Preference.Adapter<T> {

    override fun get(key: String, preferences: Preferences): T {
        val value = preferences.get(key, null)
        return java.lang.Enum.valueOf(enumClass, value)
    }

    override fun set(key: String, value: T, preferences: Preferences) {
        preferences.put(key, value.name)
    }

}
