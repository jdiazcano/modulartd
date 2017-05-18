package com.jdiazcano.modulartd.ui.widgets

/**
 * Base adapter for a List which will be the base adapter for every list, which will hold the updates and etc.
 * Created by Javier on 15/11/2014.
 */
interface BaseList<O, V> {

    fun getCount(): Int
    fun getView(position: Int, lastView: V?): V
    fun getItem(position: Int): O
    fun notifyDataSetChanged()
    fun addItem(item: O)
    fun removeItem(item: O): Boolean
    fun removeItem(index: Int): O?
    fun clearSelection()
    fun hasSelection(): Boolean
    fun selectItem(index: Int)
    fun selectItem(item: O)
    fun selectView(view: V)
}
