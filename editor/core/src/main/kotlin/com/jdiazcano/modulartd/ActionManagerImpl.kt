package com.jdiazcano.modulartd

import com.jdiazcano.modulartd.plugins.actions.Action
import mu.KLoggable
import mu.KLogger

interface ActionManager {
    /**
     * Registers an action and sends it to the Bus.
     *
     * @throws IllegalArgumentException If the action name is already registered
     */
    fun registerAction(action: Action)

    fun findAction(actionName: String) : Action?
}

internal class ActionManagerImpl : KLoggable, ActionManager {
    override val logger: KLogger = logger()

    private val actions: MutableMap<String, Action> = mutableMapOf()

    /**
     * Registers an action and sends it to the Bus.
     *
     * @throws IllegalArgumentException If the action name is already registered
     */
    override fun registerAction(action: Action) {
        if (actions[action.name] != null) {
            throw IllegalArgumentException("Action '${action.name}' already registered")
        }

        actions.put(action.name, action)
        logger.debug { "Action '${action.name}' added" }
    }

    override fun findAction(actionName: String) : Action? = actions[actionName]
}

