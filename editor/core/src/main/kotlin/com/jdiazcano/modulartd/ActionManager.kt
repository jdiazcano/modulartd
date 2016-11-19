package com.jdiazcano.modulartd

import com.jdiazcano.modulartd.plugins.actions.Action

internal object ActionManager {
    private var actions: MutableMap<String, Action> = mutableMapOf()
    private var listeners: MutableList<RegisteredActionListener> = mutableListOf()

    /**
     * Registers an action and sends it to the listeners.
     *
     * @throws IllegalArgumentException If the action name is already registered
     */
    fun registerAction(action: Action) {
        if (actions[action.name] != null) {
            throw IllegalArgumentException("Action ${action.name} already registered")
        }

        actions.put(action.name, action)
        listeners.forEach { it.process(action, actions[action.parentName]) }
    }

    /**
     * Adds a listener that will be called once a new action is registered
     */
    fun addListener(listener: RegisteredActionListener) = listeners.add(listener)

    fun findParent(action: Action) : Action? = actions[action.parentName]
}

/**
 * Declares the method that will be called everytime an action is registered in the ActionManager
 */
interface RegisteredActionListener {
    /**
     * @param action The action that has just been registered
     * @param parentAction The parent action of the action being registered and this might be null. Remember that you
     *                     always have to register the parent actions before registering their children.
     */
    fun process(action: Action, parentAction: Action?)
}
