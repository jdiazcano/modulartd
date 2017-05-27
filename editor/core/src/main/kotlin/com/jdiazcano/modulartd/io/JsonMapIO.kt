package com.jdiazcano.modulartd.io

import com.badlogic.gdx.files.FileHandle
import com.google.gson.GsonBuilder
import com.jdiazcano.modulartd.beans.Map
import java.io.IOException

/**
 * Json implementation for MapIO. This will be initialised with reflection
 */
class JsonMapIO : FileIO<Map> {

    private val parser = GsonBuilder().create()

    @Throws(MapDeserializationException::class)
    override fun read(file: FileHandle): Map {
        try {
            return parser.fromJson(file.reader(), Map::class.java)
        } catch (e: IOException) {
            throw MapDeserializationException("Error deserialising Json", e)
        }

    }

    @Throws(MapSerializationException::class)
    override fun write(obj: Map, file: FileHandle) {
        try {
            file.writeString(parser.toJson(obj), false, "UTF-8")
        } catch (e: IOException) {
            throw MapSerializationException("Failed to write json to a file", e)
        }

    }
}