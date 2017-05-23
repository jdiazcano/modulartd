package com.jdiazcano.modulartd.keys

// TODO Shortcuts should be by actor, each actor should have a different shortcut and general ones. For example if you
// TODO Ctrl+C (or Cmd+C) while the list is selected then you should be able to copy and paste the item in the list!!!
data class ShortCut(val key: Int = -1,  val modifiers: Modifiers = Modifiers())