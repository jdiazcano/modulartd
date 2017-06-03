package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.files.FileHandle
import com.jdiazcano.modulartd.config.Translations
import mu.KLogging
import java.io.File
import java.net.URL

// Handy methods for different classes but they do not belong to any specific class/file!

fun FileHandle.toURL(): URL = file().toURI().toURL()
fun FileHandle.readUtf8() : String = readString("UTF-8")

fun File.toURL() = toURI().toURL()

val globalLogger = KLogging()

fun <T, V> MutableMap<T, V>.getOrThrow(key: T, throwable: Throwable = IllegalArgumentException("Unknown value for key $key")) : V {
    val value = get(key)
    if (value != null) {
        return value
    } else {
        throw throwable
    }
}

/**
 * Translate a key and uses itself as default so it is possible to identify which key is missing in the source
 */
fun translate(key: String): String {
    val translation = translate(key, key)
    if (translation == key) {
        globalLogger.logger.warn { "Missing translation for: $key" }
    }
    return translation
}

/**
 * Calls the translations of with a default
 */
private fun translate(key: String, default: String) = Translations.of(key, default)

/**
 * Returns the text if the boolean value is true, else it will return an empty string.
 */
fun Boolean.text(string: String): String = if (this) string else ""

