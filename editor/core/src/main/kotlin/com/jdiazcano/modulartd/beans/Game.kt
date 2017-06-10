package com.jdiazcano.modulartd.beans

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle

data class Game(
        // Holds the game folder that is loaded right now into the editor
        var gameFolder: FileHandle = Gdx.files.absolute(""),
        // Denotes if the editor has unsaved changes
        private var _dirty: Boolean = false,
        // Holds the title, libgdx by default will not hold the title of the window!
        private var _title: String = "ModularTD"
) {
    var title = _title
    set(value) {
        field = value
        Gdx.graphics.setTitle(value)
    }

    var dirty = _dirty
    set(value) {
        field = value
        if (value) {
            Gdx.graphics.setTitle(title + " *")
        } else {
            Gdx.graphics.setTitle(title)
        }
    }
}