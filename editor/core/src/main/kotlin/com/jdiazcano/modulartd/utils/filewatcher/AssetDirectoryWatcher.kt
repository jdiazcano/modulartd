package com.jdiazcano.modulartd.utils.filewatcher

import com.badlogic.gdx.files.FileHandle
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.utils.isAtlasFile
import com.jdiazcano.modulartd.utils.resourceType

/**
 * This class will be attached to the system waiting for changes in files. Inside the .itd folder (Configs.editor().gameConfigFolder())
 * there should be two more folders that will contain the sounds and the images (soon(TM) it will be possible to just drag the resources
 * inside the editor and they will be copied/imported automatically). So normally it will be used with these two directories.
 *
 * TODO Scan for current images in these directories when a new map is created, NOT when is opened. When is opened, the
 * TODO GAMEFILE should be the one containing the resources and shall be loaded that way. This will be unlikely to happen though.
 * TODO IMHO this is unlikely to happen but it really would help me with testing :D
 */
class AssetDirectoryWatcher(
        vararg val watchers: DirectoryWatcher
): WatchListener {
    init {
        watchers.forEach { it.addListener(this) }
    }

    override fun created(file: FileHandle) {
        if (file.isDirectory) {
            file.list().forEach {
                if (it.isDirectory) {
                    created(it)
                } else if (!file.isAtlasFile()) {
                    val resource = Resource(file, file.resourceType())
                    Bus.post(resource, BusTopic.CREATED)
                }
            }
        } else if (!file.isAtlasFile()) {
            val resource = Resource(file, file.resourceType())
            Bus.post(resource, BusTopic.CREATED)
        }
    }

    override fun deleted(file: FileHandle) {
        if (file.isDirectory) {
            file.list().forEach {
                if (it.isDirectory) {
                    deleted(it)
                } else {
                    val resource = Resource(file, file.resourceType())
                    Bus.post(resource, BusTopic.DELETED)
                }
            }
        } else {
            val resource = Resource(file, file.resourceType())
            Bus.post(resource, BusTopic.DELETED)
        }
    }

    override fun modified(file: FileHandle) {
        val resource = Resource(file, file.resourceType())
        Bus.post(resource, BusTopic.UPDATED)
    }

    fun startWatching() {
        watchers.forEach { it.start() }
    }

    fun stopWatching() {
        watchers.forEach { it.stop() }
    }
}