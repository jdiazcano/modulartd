package com.jdiazcano.modulartd.bus

import com.jdiazcano.modulartd.BaseTest
import com.jdiazcano.modulartd.GdxTestRunner
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(GdxTestRunner::class)
class BusTest : BaseTest() {

    @Test
    fun sendStringTest() {
        val subscription = Bus.register<String>(BusTopic.CREATED) {
            assert(it == "mycoolstring") {
                "Received the wrong string in the bus: $it"
            }
        }

        Bus.post("mycoolstring", BusTopic.CREATED)
        subscription.unsubscribe()
    }

    @Test
    fun unsubscribeTest() {
        val subscription = Bus.register<String>(BusTopic.CREATED) {
            assert(false) {
                "This should be never called"
            }
        }
        subscription.unsubscribe()
        Bus.post("mycoolstring", BusTopic.CREATED)
    }

    @Test
    fun multipleSubscriberTest() {
        val subscription = Bus.register<String>(BusTopic.CREATED) {
            assert(it == "mycoolstring") {
                "Received the wrong string in the bus: $it"
            }
        }
        val override = Bus.register<String>(BusTopic.CREATED) {
            assert(it == "mycoolstring") {
                "Received the wrong string in the bus: $it"
            }
        }
        Bus.post("mycoolstring", BusTopic.CREATED)

        subscription.unsubscribe()
        override.unsubscribe()
    }

    @Test
    fun differentPostTest() {
        data class CoolClass(val id: Int)

        val subscription = Bus.register<CoolClass>(BusTopic.CREATED) {
            assert(it.id == 2) {
                "Received the wrong ID in the bus: $it"
            }
        }
        Bus.post(CoolClass(2), BusTopic.CREATED)
        subscription.unsubscribe()
    }
}