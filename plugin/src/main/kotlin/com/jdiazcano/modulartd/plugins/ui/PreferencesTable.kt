package com.jdiazcano.modulartd.plugins.ui

import com.kotcrab.vis.ui.widget.VisTable

/**
 * A preferences table defines the table that will be rendered when the user clicks on the preference's item of the
 * plugin.
 */
abstract class PreferencesTable(name: String) : VisTable(), Persistable {

    init {
        setName(name)
    }

}

/**
 * This interface defines that something is persistable. The methods will receive/return a map with the serialized
 * properties.
 */
interface Persistable {

    /**
     * Serializes the properties.
     *
     * @return A map containing the properties for your plugin.
     */
    fun serialize(): Map<String, String>

    /**
     * Deserializes the properties. Same properties that you serialize will be here when this method is called.
     */
    fun deserialize(properties: Map<String, String>)
}
