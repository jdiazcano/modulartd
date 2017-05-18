package com.jdiazcano.modulartd.bus

import rx.lang.kotlin.PublishSubject
import rx.subjects.SerializedSubject

/**
 * Bus that will be used to send events with different topics like "created", "updated"
 */
@Suppress("UNCHECKED_CAST")
object Bus {

    private val buses = mutableMapOf(BusTopic.DEFAULT to SerializedSubject(PublishSubject<Any>()))

    /**
     * Registers a function that will be called once we receive an item of that class in that theme
     */
    fun <K> register(type: Class<*>, topic: BusTopic, action: (K) -> Any) {
        buses.getOrPut(topic) { SerializedSubject(PublishSubject<Any>()) }
                .filter { type.isAssignableFrom(it.javaClass) }
                .subscribe { action(it as K) }
    }

    /**
     * Posts an item to a theme
     */
    fun post(obj: Any, topic: BusTopic = BusTopic.DEFAULT) {
        if (topic in buses) {
            buses[topic]!!.onNext(obj)
        }
    }
}