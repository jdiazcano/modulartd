package com.jdiazcano.modulartd.io

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Json
import com.jdiazcano.modulartd.beans.Map
import java.io.IOException

/**
 * Json implementation for MapIO. This will be initialised with reflection

 * Created by javierdiaz on 21/12/2015.
 */
class JsonMapIO : FileIO<Map> {

    private val parser = Json()

    @Throws(MapDeserializationException::class)
    override fun read(file: FileHandle): Map {
        try {
            return parser.fromJson(Map::class.java, file.read())
        } catch (e: IOException) {
            throw MapDeserializationException("Error deserialising Json", e)
        }

    }

    @Throws(MapSerializationException::class)
    override fun write(game: Map, file: FileHandle) {
        try {
            // file.child(".itd").child(editor.gameFileName())
            parser.toJson(game, file)
        } catch (e: IOException) {
            throw MapSerializationException("Failed to write json to a file", e)
        }

    }
}