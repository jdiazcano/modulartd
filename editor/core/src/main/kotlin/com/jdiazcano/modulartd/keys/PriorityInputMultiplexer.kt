package com.jdiazcano.modulartd.keys

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic

/**
 *
 */
class PriorityInputMultiplexer(stage: Stage) : InputProcessor {
    private val processors = sortedSetOf<PriorityProcessor>(compareBy { -it.priority })

    init {
        Bus.register<PriorityProcessor>(PriorityProcessor::class.java, BusTopic.PROCESSOR_REGISTERED) {
            addProcessor(it)
            println("Processor added.")
        }

        Bus.register<PriorityProcessor>(PriorityProcessor::class.java, BusTopic.PROCESSOR_UNREGISTERED) {
            removeProcessor(it)
        }

        Bus.post(ShortCutInputProcessor(stage))
    }

    fun addProcessor(processor: PriorityProcessor) {
        processors.add(processor)
    }

    fun removeProcessor(processor: PriorityProcessor) {
        processors.remove(processor)
    }

    fun size(): Int {
        return processors.size
    }

    fun clear() {
        processors.clear()
    }

    override fun keyDown(keycode: Int): Boolean {
        processors.forEach {
            if (it.keyDown(keycode)) {
                return true
            }
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        processors.forEach {
            if (it.keyUp(keycode)) {
                return true
            }
        }
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        processors.forEach {
            if (it.keyTyped(character)) {
                return true
            }
        }
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        processors.forEach {
            if (it.touchDown(screenX, screenY, pointer, button)) {
                return true
            }
        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        processors.forEach {
            if (it.touchUp(screenX, screenY, pointer, button)) {
                return true
            }
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        processors.forEach {
            if (it.touchDragged(screenX, screenY, pointer)) {
                return true
            }
        }
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        processors.forEach {
            if (it.mouseMoved(screenX, screenY)) {
                return true
            }
        }
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        processors.forEach {
            if (it.scrolled(amount)) {
                return true
            }
        }
        return false
    }
}

data class PriorityProcessor(val processor: InputProcessor, val priority: Int = 0) : InputProcessor by processor

object Priorities {
    // This should be used only once in the stage
    const val STAGE = 99999
    // This should also be used only once in the global shortcuts defined by bundled plugins (probably)
    const val GLOBAL_SHORTCUTS = 10000
    // This will be used to have the priority of the current focused window
    const val FOCUSED_WINDOW = 5000
}