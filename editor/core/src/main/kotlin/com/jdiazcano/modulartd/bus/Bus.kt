package com.jdiazcano.modulartd.bus

import rx.Subscription
import rx.lang.kotlin.PublishSubject
import rx.subjects.SerializedSubject

/**
 * Bus that will be used to send events with different topics like "created", "updated"
 */
@Suppress("UNCHECKED_CAST")
object Bus {

    val buses = mutableMapOf(BusTopic.DEFAULT to SerializedSubject(PublishSubject<Any>()))

    /**
     * Registers a function that will be called once we receive an item of that class in that theme
     */
    inline fun <reified K> register(topic: BusTopic, crossinline action: (K) -> Any): Subscription {
        return buses.getOrPut(topic) { SerializedSubject(PublishSubject<Any>()) }
                .filter { K::class.java.isAssignableFrom(it.javaClass) }
                .subscribe { action(it as K) }
    }

    fun <K> register(clazz: Class<*>, topic: BusTopic, action: (K) -> Any): Subscription {
        return buses.getOrPut(topic) { SerializedSubject(PublishSubject<Any>()) }
                .filter { clazz.isAssignableFrom(it.javaClass) }
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