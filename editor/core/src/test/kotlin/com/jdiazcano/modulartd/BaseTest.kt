package com.jdiazcano.modulartd

import com.github.salomonbrys.kodein.Kodein
import com.jdiazcano.modulartd.injections.kodein

open class BaseTest {
    val injector = Kodein {
        // Import all the normal injections
        extend(kodein)

        // From now on here there will be the test mocks

    }
}