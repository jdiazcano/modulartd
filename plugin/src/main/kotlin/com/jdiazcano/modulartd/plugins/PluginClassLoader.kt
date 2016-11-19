package com.jdiazcano.modulartd.plugins

import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.net.URL
import java.net.URLClassLoader

@Suppress("UNCHECKED_CAST")
class PluginClassLoader(urls: Array<out URL>?) : URLClassLoader(urls) {

    fun loadPlugin(className: String) : Class<Plugin> {
        val c = loadClass(className)
        if (!Plugin::class.java.isAssignableFrom(c)) {
            throw IllegalArgumentException("This class is not a plugin")
        }

        val pluginClass = c as Class<Plugin>
        checkPluginMethods(pluginClass)
        return pluginClass
    }

    private fun checkPluginMethods(pluginClass: Class<Plugin>) {
        val baseMethods = Plugin::class.java.methods
        val implMethods = pluginClass.methods.associateBy( {it.name}, {it} )

        baseMethods.forEach { method ->
            val methodName = method.name

            if (implMethods.containsKey(methodName)) {
                val methodImpl = implMethods[methodName]!!
                assertNotAbstract(pluginClass, methodImpl)
                assertReturnTypesEqual(pluginClass, methodImpl, method)
                assertParametersMatch(pluginClass, methodImpl, method)
            } else {
                throw InvalidPluginVersionException("Class ${pluginClass.name} does not have the method $methodName")
            }
        }
    }

    private fun assertParametersMatch(pluginClass: Class<Plugin>, methodImpl: Method, method: Method) {
        val interfaceParameters = method.parameterTypes
        val implParameters = methodImpl.parameterTypes

        if (interfaceParameters.size != implParameters.size) {
            throw InvalidPluginVersionException("Plugin error: ${pluginClass.name}. Not enough parameters, expected $interfaceParameters and found $implParameters on method ${method.name}")
        }

        interfaceParameters.forEachIndexed { i, interfaceParameter ->
            if (!interfaceParameter.isAssignableFrom(implParameters[i])) {
                throw InvalidPluginVersionException("Plugin error: ${pluginClass.name}. Method types are not compatible for method ${method.name}. Expected ${interfaceParameter.canonicalName}, found ${implParameters[i].canonicalName}")
            }
        }
    }

    private fun assertReturnTypesEqual(pluginClass: Class<Plugin>, methodImpl: Method, method: Method) {
        if (!method.returnType.isAssignableFrom(methodImpl.returnType)) {
            throw InvalidPluginVersionException("Plugin error: ${pluginClass.name}. Return types must be the assignable from ${method.returnType.name} and ${methodImpl.returnType.name} was found")
        }
    }

    private fun assertNotAbstract(pluginClass: Class<Plugin>, currentMethod: Method) {
        val result = currentMethod.modifiers and Modifier.ABSTRACT
        if (result != 0) {
            throw InvalidPluginVersionException("Plugin error: ${pluginClass.name}. Method ${currentMethod.name} is currently abstract, all methods must provide implementation")
        }
    }

}