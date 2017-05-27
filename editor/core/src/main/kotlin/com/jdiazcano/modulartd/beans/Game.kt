package com.jdiazcano.modulartd.beans

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle

data class Game(
        // Holds the game folder that is loaded right now into the editor
        var gameFolder: FileHandle = Gdx.files.absolute(""),
        // Denotes if the editor has unsaved changes
        var dirty: Boolean = false
)
