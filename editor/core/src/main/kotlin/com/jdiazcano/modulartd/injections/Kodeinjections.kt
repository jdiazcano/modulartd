package com.jdiazcano.modulartd.injections

import com.github.salomonbrys.kodein.*
import com.jdiazcano.modulartd.ResourceManager
import com.jdiazcano.modulartd.beans.Game
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.beans.MapObjectRenderer
import com.jdiazcano.modulartd.io.FileIO
import com.jdiazcano.modulartd.io.JsonMapIO
import com.jdiazcano.modulartd.utils.filewatcher.AssetDirectoryWatcher
import com.jdiazcano.modulartd.utils.filewatcher.DirectoryWatcher
import com.jdiazcano.modulartd.utils.toPath

val kodein = Kodein {
    bind<Map>() with singleton { Map() }
    bind<Game>() with singleton { Game() }
    bind<FileIO<Map>>() with singleton { JsonMapIO() }
    bind<MapObjectRenderer>() with singleton { MapObjectRenderer() }
    bind<ResourceManager>() with eagerSingleton { ResourceManager(instance()) }
    bind<AssetDirectoryWatcher>() with provider {
        val game = kodein.instance<Game>()
        AssetDirectoryWatcher(
                DirectoryWatcher(game.gameFolder.child("images").toPath()),
                DirectoryWatcher(game.gameFolder.child("sounds").toPath())

        )
    }
}