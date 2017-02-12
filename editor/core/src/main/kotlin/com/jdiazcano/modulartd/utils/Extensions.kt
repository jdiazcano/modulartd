package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.files.FileHandle
import java.io.File
import java.net.URL

// Handy methods for different classes but they do not belong to any specific class/file!

fun FileHandle.toURL(): URL = file().toURI().toURL()
fun FileHandle.readUtf8() : String = readString("UTF-8")

fun File.toURL() = toURI().toURL()