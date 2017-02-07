package com.jdiazcano.modulartd.plugins.actions


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class RegisterAction(val id: String)