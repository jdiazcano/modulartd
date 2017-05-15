package com.jdiazcano.modulartd.keys

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.jdiazcano.modulartd.ParentedAction
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.plugins.actions.Actioned
import com.jdiazcano.modulartd.ui.StageWrapperImpl
import mu.KLogging

/**
 * The shortcut input processor will take care of all the global shortcuts
 */
class ShortCutInputProcessor(stage: Stage) : InputAdapter() {
    companion object : KLogging()

    private val wrappedStage = StageWrapperImpl(stage)
    private val actionMap = mutableMapOf<ShortCut, Actioned>()
    private var isControl = false
    private var isShift = false
    private var isCommand = false
    private var isAlt = false

    init {

        Bus.register(ParentedAction::class.java, BusTopic.ACTION_REGISTERED) {
            actionMap[it.action.shortCut] = it.action
            logger.debug { "Added action ${it.action.name}" }
        }

        Bus.register(ShortCutAction::class.java, BusTopic.SHORTCUT_UPDATED) {
            actionMap[it.shortCut] = it.action
            logger.debug { "Shortcut updated : ${KeyPrinters.print(it.shortCut)}" }
        }

        Bus.register(ParentedAction::class.java, BusTopic.ACTION_UNREGISTERED) {
            actionMap.remove(it.action.shortCut)
            logger.debug { "Removed action ${it.action.name}" }
        }

        Bus.post(PriorityProcessor(this, Priorities.GLOBAL_SHORTCUTS), BusTopic.PROCESSOR_REGISTERED)
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) {
            isControl = true
            return false
        }
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            isShift = true
            return false
        }
        if (keycode == Input.Keys.ALT_LEFT || keycode == Input.Keys.ALT_RIGHT) {
            isAlt = true
            return false
        }
        if (keycode == Input.Keys.SYM) {
            isCommand = true
            return false
        }

        // If we do not have any modifiers we will not continue firing anything
        if (!isControl && !isShift && !isCommand && !isAlt) {
            return false
        }

        // A key that isn't a modifier has been pressed so we just build the shortcut for it and check
        val shortCut = ShortCut(keycode, Modifiers(
                alt = isAlt,
                control = isControl,
                command = isCommand,
                shift = isShift
        ))
        val action = actionMap[shortCut]
        if (action != null) {
            action.perform(wrappedStage)
            return true
        }

        return super.keyDown(keycode)
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT) {
            isControl = false
            return false
        }
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            isShift = false
            return false
        }
        if (keycode == Input.Keys.SYM) {
            isCommand = false
            return false
        }
        if (keycode == Input.Keys.ALT_LEFT || keycode == Input.Keys.ALT_RIGHT) {
            isAlt = false
            return false
        }

        return false
    }
}