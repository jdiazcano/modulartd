package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import java.io.File
import java.net.URL

// Handy methods for different classes but they do not belong to any specific class/file!

fun FileHandle.toURL(): URL = file().toURI().toURL()
fun FileHandle.readUtf8() : String = readString("UTF-8")

fun File.toURL() = toURI().toURL()

// GDX Extensions
fun Actor.clickListener(action: (InputEvent, Float, Float) -> Unit) {
    addListener(object : ClickListener() {
        override fun clicked(event: InputEvent, x: Float, y: Float) {
            action(event, x, y)
        }
    })
}

fun Actor.changeListener(action: (ChangeListener.ChangeEvent, Actor) -> Unit) {
    addListener(object : ChangeListener() {
        override fun changed(event: ChangeEvent, actor: Actor) {
            action(event, actor)
        }
    })
}