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
     * Deleted objects, for example a turret is deletd
     */
    DELETED,
    /**
     * Updated objects, for example the shoot speed of a turret
     */
    UPDATED,
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
     *
     */
    PREFERENCES_REGISTERED,
}