package com.jdiazcano.modulartd

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Disposable
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.utils.SpriteLoader

/**
 * This will be the resource manager that will become the handler for all the resources. A resource is a file auto loaded
 * from a file which can be of any of the ResourceType(s)
 */
class ResourceManager: Disposable {

    companion object {
        val NO_SOUND = Resource(Gdx.files.absolute(" - No sound - "), ResourceType.SOUND)
    }

    private val manager = AssetManager(AbsoluteFileHandleResolver())
    private val resources = arrayListOf<Resource>()

    init {
        // Custom handler for the Sprites because they actually need to load different images all corresponding to the
        // same sprite.
        manager.setLoader(Sprite::class.java, SpriteLoader(AbsoluteFileHandleResolver()))

        Bus.register<Resource>(Resource::class.java, BusTopic.CREATED) {
            addResource(it)
        }

        Bus.register<Resource>(Resource::class.java, BusTopic.DELETED) {
            removeResource(it)
        }

        // The NO_SOUND needs to be addressed because an action is not mandatory to have a sound
        Bus.post(NO_SOUND, BusTopic.CREATED)
    }

    /**
     * Removes a resource from the manager to free up space.
     *
     * This should not be used directly because the bus will catch the delete event and should remove it automatically
     */
    private fun removeResource(resource: Resource) {
        resources.remove(resource)
        manager.unload(resource.file.path())
    }

    /**
     * Adds a resource to the manager.
     *
     * This should not be used directly because the bus should catch the event and act accordingly.
     */
    private fun addResource(resource: Resource) {
        resources.add(resource)
        manager.load(resource.file.path(), resource.assetType())
    }

    /**
     * Filter resources by type.
     *
     * Mostly used for combo boxes where you can select the resource to use.
     */
    fun getResourcesByType(type: ResourceType) = resources.filter { it.resourceType == type }

    fun getAssetsByType(type: ResourceType) = getResourcesByType(type).map { manager.get(it.file.path(), it.assetType()) }

    fun isLoaded(path: String) = manager.isLoaded(path)

    fun exists(resource: Resource) = resources.contains(resource)

    override fun dispose() {
        manager.dispose()
        resources.clear()
    }

}