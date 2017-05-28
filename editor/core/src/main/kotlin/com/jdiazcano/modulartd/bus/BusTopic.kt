package com.jdiazcano.modulartd.bus

/**
 * List of topics for the Bus. Everything will come here so there is a controlled growth of topics and a control of
 * where and when we post things to the Bus.
 */
enum class BusTopic {
    /**
     * Default topic, everything will be here but its usage is discouraged
     */
    DEFAULT,

    /**
     * Every new object created, like a turret for example will come to this topic
     */
    CREATED,

    /**
     * Deleted objects, for example a turret (or a map!!) is deleted
     */
    DELETED,

    /**
     * Updated objects, for example the shoot speed of a turret
     */
    UPDATED,

    /**
     * This will be triggered when some kind of object is selected for example in the CreatorsList or somewhere so other
     * parts of the software can subscribe to this event and act properly
     */
    SELECTED,

    /**
     * Everytime a setting is changed (or group of settings), will be posted to this topic
     *
     * TODO I ported RxPreferences to have Observable preferences, I must make a decision in order to use one or another
     */
    SETTING_CHANGED,

    /**
     * When a plugin is registered this topic will be updated with a ParentedAction
     */
    PLUGIN_REGISTERED,

    /**
     * This will be fired when a method in a plugin is annotated with @Preferences and will return a PreferencesTable
     */
    PREFERENCES_REGISTERED,

    /**
     * Fired when a processor is registered. The input processors will take care of the inputs, for example there will
     * be the stage input processor and the shortcut input processor for example. There might even different processors
     * for different windows focus
     */
    PROCESSOR_REGISTERED,

    /**
     * Fired when a processor is unregistered.
     */
    PROCESSOR_UNREGISTERED,

    /**
     * Fired when an action is registered. This is a method annotated with @RegisterAction in a plugin.
     */
    ACTION_REGISTERED,

    /**
     * Fired when an action is removed
     */
    ACTION_UNREGISTERED,

    /**
     * A shortcut has been updated, I'll probably had to make it remove the old one (or attempt to make it execute
     * multiple actions with only one shortcut but I don't think is worth right now)
     */
    SHORTCUT_UPDATED,

    /**
     * A new dialog that will mostly be caught by the scene so it can pass objects around
     */
    NEW_DIALOG,

    /**
     * This will be invoked when a map is loaded (or created an loaded right after)
     */
    MAP_LOAD,

    /**
     * When something has finished doing something this will be fired.
     */
    LOAD_FINISHED,

    /**
     * Fired when a map is reloaded and the resources must be cleared and reinserted
     */
    RESOURCES_RELOAD,

    /**
     * Reset everything!
     */
    RESET,
}