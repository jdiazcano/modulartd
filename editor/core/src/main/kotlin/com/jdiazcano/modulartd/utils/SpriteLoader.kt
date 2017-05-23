package com.jdiazcano.modulartd.utils

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetLoaderParameters
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.TextureData
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Array

class SpriteLoader(resolver: FileHandleResolver = InternalFileHandleResolver())
    : AsynchronousAssetLoader<Sprite, SpriteLoader.TextureParameter>(resolver) {

    class SpriteLoaderInfo {
        internal var filename: String? = null
        internal var data: TextureData? = null
        internal var texture: Texture? = null
    }

    internal var info: SpriteLoaderInfo? = SpriteLoaderInfo()

    override fun loadAsync(manager: AssetManager, fileName: String, file: FileHandle, parameter: TextureParameter?) {
        info!!.filename = fileName
        if (parameter == null || parameter.textureData == null) {
            var format: Pixmap.Format? = null
            var genMipMaps = false
            info!!.texture = null

            if (parameter != null) {
                format = parameter.format
                genMipMaps = parameter.genMipMaps
                info!!.texture = parameter.texture
            }

            info!!.data = TextureData.Factory.loadFromFile(file, format, genMipMaps)
        } else {
            info!!.data = parameter.textureData
            info!!.texture = parameter.texture
        }
        if (!info!!.data!!.isPrepared) info!!.data!!.prepare()
    }

    override fun loadSync(manager: AssetManager, fileName: String, file: FileHandle, parameter: TextureParameter?): Sprite? {
        if (info == null) return null
        var texture = info!!.texture
        if (texture != null) {
            texture.load(info!!.data!!)
        } else {
            texture = Texture(info!!.data!!)
        }
        if (parameter != null) {
            texture.setFilter(parameter.minFilter, parameter.magFilter)
            texture.setWrap(parameter.wrapU, parameter.wrapV)
        }
        return Sprite(texture)
    }

    override fun getDependencies(fileName: String, file: FileHandle, parameter: TextureParameter): Array<AssetDescriptor<*>>? {
        return null
    }

    class TextureParameter : AssetLoaderParameters<Sprite>() {
        /** the format of the final Texture. Uses the source images format if null  */
        var format: Pixmap.Format? = null
        /** whether to generate mipmaps  */
        var genMipMaps = false
        /** The sprite to put the [TextureData] in, optional.  */
        var texture: Texture? = null
        /** TextureData for textures created on the fly, optional. When set, all format and genMipMaps are ignored  */
        var textureData: TextureData? = null
        var minFilter = Texture.TextureFilter.Nearest
        var magFilter = Texture.TextureFilter.Nearest
        var wrapU = Texture.TextureWrap.ClampToEdge
        var wrapV = Texture.TextureWrap.ClampToEdge
    }
}