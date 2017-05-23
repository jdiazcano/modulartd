package com.jdiazcano.modulartd.injections

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import com.jdiazcano.modulartd.beans.Game
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.beans.MapObjectRenderer
import com.jdiazcano.modulartd.io.FileIO
import com.jdiazcano.modulartd.io.JsonMapIO

val kodein = Kodein {
    bind<Map>() with singleton { Map() }
    bind<Game>() with singleton { Game() }
    bind<FileIO<Map>>() with singleton { JsonMapIO() }
    bind<MapObjectRenderer>() with singleton { MapObjectRenderer() }
}