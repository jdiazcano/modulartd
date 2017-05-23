package com.jdiazcano.modulartd.io

import com.badlogic.gdx.files.FileHandle

/**
 * Definition of how an object should be written and read from a file.
 */
interface FileIO<T> {

    /**
     * Read the object from the file.
     */
    @Throws(MapDeserializationException::class)
    fun read(file: FileHandle): T

    /**
     * Writes the object into a file. This should have a compatible format for reading!
     */
    @Throws(MapSerializationException::class)
    fun write(obj: T, file: FileHandle)
}

/**
 * Something has gone wrong with the serialization of the game. This will mostly happen if the versioning is not handled
 * properly.
 *
 * To always serialize properly no name changes shall occur, only adding fields! A strategy to follow would be to deprecate
 * a property and 2 or 3 versions later just delete it so all the maps would be compatible, but being forward compatible
 * will not be easy :(
 */
class MapSerializationException(message: String, cause: Throwable): Exception(message, cause)

/**
 * Something went wrong with the deserialization of the game. This will mostly happen if the versioning is not handled
 * properly.
 */
class MapDeserializationException(message: String, cause: Throwable): Exception(message, cause)