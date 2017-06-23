package com.jdiazcano.modulartd.injections

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.github.salomonbrys.kodein.*
import com.jdiazcano.modulartd.ActionManager
import com.jdiazcano.modulartd.ActionManagerImpl
import com.jdiazcano.modulartd.ResourceManager
import com.jdiazcano.modulartd.beans.Game
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.io.FileIO
import com.jdiazcano.modulartd.io.JsonMapIO
import com.jdiazcano.modulartd.utils.filewatcher.AssetDirectoryWatcher
import com.jdiazcano.modulartd.utils.filewatcher.DirectoryWatcher
import com.jdiazcano.modulartd.utils.toPath
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.spinner.SimpleFloatSpinnerModel
import com.kotcrab.vis.ui.widget.spinner.SpinnerModel

val kodein = Kodein {
    bind<Map>() with singleton { Map() }
    bind<Game>() with singleton { Game() }
    bind<FileIO<Map>>() with singleton { JsonMapIO() }
    bind<ResourceManager>() with eagerSingleton { ResourceManager(instance()) }
    bind<AssetDirectoryWatcher>() with provider {
        val game = kodein.instance<Game>()
        AssetDirectoryWatcher(
                DirectoryWatcher(game.gameFolder.child("images").toPath()),
                DirectoryWatcher(game.gameFolder.child("sounds").toPath())

        )
    }
    bind<Label>("globalMessageLabel") with singleton { VisLabel() }
    bind<SpinnerModel>("float") with provider { SimpleFloatSpinnerModel(0.2F, 0.04F, 100F, 0.02F, 2) }
    bind<ActionManager>() with singleton { ActionManagerImpl() }
}