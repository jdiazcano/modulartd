package com.jdiazcano.modulartd.plugins

import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.net.URL
import java.net.URLClassLoader

@Suppress("UNCHECKED_CAST")
class PluginClassLoader(urls: Array<out URL>?) : URLClassLoader(urls) {

    override fun loadClass(name: String?): Class<*> {
        return super.loadClass(name)
    }

    fun loadPlugin(className: String) : Class<Plugin> {
        val c = loadClass(className)
        if (!Plugin::class.java.isAssignableFrom(c)) {
            throw IllegalArgumentException("This class is not a plugin")
        }

        val pluginClass = c as Class<Plugin>
        checkPluginMethods(pluginClass)
        return pluginClass
    }

    private fun  checkPluginMethods(pluginClass: Class<Plugin>) {
        val baseMethods = Plugin::class.java.methods
        val currentMethods = pluginClass.methods.associateBy ( {it.name}, {it} )

        baseMethods.forEach { method ->
            val methodName = method.name

            if (currentMethods.containsKey(methodName)) {
                val currentMethod = currentMethods[methodName]!!
                assertNotAbstract(pluginClass, currentMethod)
                assertReturnTypesEqual(pluginClass, currentMethod, method)
                assertParametersMatch(pluginClass, currentMethod, method)
            } else {
                throw InvalidPluginVersionException("Class ${pluginClass.name} does not have the method $methodName")
            }
        }
    }

    private fun  assertParametersMatch(pluginClass: Class<Plugin>, currentMethod: Method, method: Method) {

    }

    private fun assertReturnTypesEqual(pluginClass: Class<Plugin>, currentMethod: Method, method: Method) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun assertNotAbstract(pluginClass: Class<Plugin>, currentMethod: Method) {
        val result = currentMethod.modifiers.and(Modifier.ABSTRACT)
        if (result != 0) {
            throw InvalidPluginVersionException("Method ${currentMethod.name} is currently abstract, all methods must provide implementation")
        }
    }

}