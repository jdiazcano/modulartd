package com.jdiazcano.modulartd

import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.plugins.actions.Action
import mu.KLoggable
import mu.KLogger

internal object ActionManager : KLoggable {
    override val logger: KLogger = logger()

    private val actions: MutableMap<String, Action> = mutableMapOf()

    /**
     * Registers an action and sends it to the Bus.
     *
     * @throws IllegalArgumentException If the action name is already registered
     */
    fun registerAction(action: Action) {
        if (actions[action.name] != null) {
            throw IllegalArgumentException("Action '${action.name}' already registered")
        }

        actions.put(action.name, action)
        logger.debug { "Action '${action.name}' added" }
    }

    fun findAction(actionName: String) : Action? = actions[actionName]
}

data class ParentedAction(val action: Action, val parentId: String)