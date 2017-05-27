package com.jdiazcano.modulartd

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Disposable
import com.jdiazcano.modulartd.beans.Map
import com.jdiazcano.modulartd.beans.Resource
import com.jdiazcano.modulartd.beans.ResourceType
import com.jdiazcano.modulartd.bus.Bus
import com.jdiazcano.modulartd.bus.BusTopic
import com.jdiazcano.modulartd.utils.SpriteLoader
import mu.KLogging

/**
 * This will be the resource manager that will become the handler for all the resources. A resource is a file auto loaded
 * from a file which can be of any of the ResourceType(s)
 */
class ResourceManager(map: Map): Disposable {

    companion object : KLogging() {
        val NO_SOUND = Resource(Gdx.files.absolute(" - No sound - "), ResourceType.SOUND)
    }

    private val manager = AssetManager(AbsoluteFileHandleResolver())
    private var resources: MutableSet<Resource> = map.resources

    init {
        // Custom handler for the Sprites because they actually need to load different images all corresponding to the
        // same sprite.
        manager.setLoader(Sprite::class.java, SpriteLoader(AbsoluteFileHandleResolver()))

        Bus.register<Resource>(Resource::class.java, BusTopic.CREATED) {
            addResource(it)
            logger.debug { "Loaded resource: $it" }
        }

        Bus.register<Resource>(Resource::class.java, BusTopic.DELETED) {
            removeResource(it)
            logger.debug { "Deleted resource: $it" }
        }

        Bus.register<Resource>(Resource::class.java, BusTopic.DELETED) {
            if (it in resources) {
                reloadResource(it)
                logger.debug { "Reloaded resource $it" }
            }
        }

        Bus.register<MutableSet<Resource>>(MutableSet::class.java, BusTopic.RESOURCES_RELOAD) { resources ->
            this.resources = resources
            resources.forEach {
                addResource(it, true)
            }
        }
        // The NO_SOUND needs to be addressed because an action is not mandatory to have a sound
        Bus.post(NO_SOUND, BusTopic.CREATED)
    }

    private fun reloadResource(resource: Resource) {
        val path = resource.file.path()
        manager.unload(path)
        manager.load(path, resource.assetType())
        manager.finishLoadingAsset(path)
        Bus.post(resource, BusTopic.LOAD_FINISHED)
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
    private fun addResource(resource: Resource, loadOnly: Boolean = false) {
        if (!loadOnly) {
            resources.add(resource)
        }
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