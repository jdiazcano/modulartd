package com.jdiazcano.modulartd

import com.github.salomonbrys.kodein.instance
import com.jdiazcano.modulartd.plugins.bundled.ExitAction
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(GdxTestRunner::class)
class ActionManagerImplTest : BaseTest() {
    private val actionManager: ActionManager = injector.instance()
    private val action = ExitAction()

    @Test
    fun registerActionTest() {
        actionManager.registerAction(action)
        assert(actionManager.findAction(action.name) == action)
    }

    @Test(expected = IllegalArgumentException::class)
    fun repeatedActionTest() {
        actionManager.registerAction(action)
    }

}