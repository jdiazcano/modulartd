package com.jdiazcano.modulartd.utils.filewatcher

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import java.io.IOException
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class DirectoryWatcher(private val root: Path, listener: WatchListener? = null) {
    private val settleDelay: Int = 1
    private val listeners: MutableList<WatchListener> = arrayListOf()
    private val running: AtomicBoolean = AtomicBoolean(false)
    private var watchService: WatchService? = null
    private var watchThread: Thread? = null
    private val watchPathKeyMap: MutableMap<Path, WatchKey> = hashMapOf()
    private var timer: Timer? = null

    init {
        listener?.let { listeners.add(it) }
    }

    /**
     * Starts the watcher service and registers watches in all of the sub-folders of the given root folder.
     *
     * **Important:** This method returns immediately, even though the watches might not be in place yet. For large file trees,
     * it might take several seconds until all directories are being monitored. For normal cases (1-100 folders), this should not
     * take longer than a few milliseconds.
     */
    @Throws(IOException::class)
    fun start() {
        watchService = FileSystems.getDefault().newWatchService()
        watchThread = Thread({
            running.set(true)
            walkTreeAndSetWatches()
            while (running.get()) {
                try {
                    val watchKey = watchService!!.take()

                    for (event in watchKey.pollEvents()) {
                        val ev = event as WatchEvent<Path>
                        val dir = watchKey.watchable() as Path
                        val fullPath = dir.resolve(ev.context())
                        val fileHandle = Gdx.files.absolute(fullPath.toString())

                        when (ev.kind()) {
                            ENTRY_MODIFY -> Gdx.app.postRunnable { listeners.forEach { l -> l.modified(fileHandle) } }
                            ENTRY_DELETE -> Gdx.app.postRunnable { listeners.forEach { l -> l.deleted(fileHandle) } }
                            ENTRY_CREATE -> Gdx.app.postRunnable { listeners.forEach { l -> l.created(fileHandle) } }
                        }
                    }

                    watchKey.reset()
                    resetWaitSettlementTimer()
                } catch (e: InterruptedException) {
                    running.set(false)
                } catch (e: ClosedWatchServiceException) {
                    running.set(false)
                }

            }
        }, "Watcher")
        watchThread!!.start()
    }

    @Synchronized fun stop() {
        if (watchThread != null) {
            try {
                watchService!!.close()
                running.set(false)
                watchThread!!.interrupt()
            } catch (e: IOException) {
                // Don't care
            }

        }
    }

    @Synchronized private fun resetWaitSettlementTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        timer = Timer("WatchTimer")
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                walkTreeAndSetWatches()
                unregisterStaleWatches()
            }
        }, settleDelay.toLong())
    }

    @Synchronized private fun walkTreeAndSetWatches() {
        try {
            Files.walkFileTree(root, object : FileVisitor<Path?> {
                @Throws(IOException::class)
                override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                    dir?.let { registerWatch(it) }
                    return FileVisitResult.CONTINUE
                }

                @Throws(IOException::class)
                override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
                    return FileVisitResult.CONTINUE
                }

                @Throws(IOException::class)
                override fun visitFileFailed(file: Path?, exc: IOException?): FileVisitResult {
                    return FileVisitResult.CONTINUE
                }

                @Throws(IOException::class)
                override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult {
                    return FileVisitResult.CONTINUE
                }
            })
        } catch (e: IOException) {
            // Don't care
        }

    }

    @Synchronized private fun unregisterStaleWatches() {
        watchPathKeyMap.keys
                .filterNot { Files.exists(it, LinkOption.NOFOLLOW_LINKS) }
                .forEach { this.unregisterWatch(it) }
    }

    @Synchronized private fun registerWatch(dir: Path) {
        if (!watchPathKeyMap.containsKey(dir)) {
            try {
                val watchKey = dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY, OVERFLOW)
                watchPathKeyMap.put(dir, watchKey)
            } catch (e: IOException) {
                // Don't care!
            }

        }
    }

    @Synchronized private fun unregisterWatch(dir: Path) {
        val watchKey = watchPathKeyMap[dir]
        if (watchKey != null) {
            watchKey.cancel()
            watchPathKeyMap.remove(dir)
        }
    }

    fun addListener(listener: WatchListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: WatchListener): Boolean {
        return listeners.remove(listener)
    }

}

interface WatchListener {
    /**
     * Called when a file has been modified. For example you add some text to a .txt file
     */
    fun modified(file: FileHandle) {}

    /**
     * Called when a file has been deleted.
     */
    fun deleted(file: FileHandle) {}

    /**
     * Called when a file has been created.
     */
    fun created(file: FileHandle) {}
}