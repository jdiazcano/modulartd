package com.jdiazcano.modulartd.beans

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle

data class Game(
        var gameFolder: FileHandle = Gdx.files.absolute("")
)
