package com.jdiazcano.modulartd.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jdiazcano.modulartd.ModularTD;
import com.jdiazcano.modulartd.config.Configs;
import com.jdiazcano.rxpreferences.RxDesktopPreferences;
import org.lwjgl.opengl.Display;

/** Launches the desktop (LWJGL) application. */
// TODO Translate this to kotlin!
public class DesktopLauncher {
    public static void main(String[] args) {
        createApplication();
    }
    private static RxDesktopPreferences preferences = Configs.INSTANCE.getPreferences();

    private static LwjglApplication createApplication() {
        return new LwjglApplication(new ModularTD(), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        addShutdownHook();
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "ModularTD";
        configuration.width = preferences.getInteger("window.size.x", 1080).get();;
        configuration.height = preferences.getInteger("window.size.y", 720).get();;
        configuration.x = preferences.getInteger("window.location.x", 0).get();
        configuration.y = preferences.getInteger("window.location.y", 0).get();
        for (int size : new int[] { 128, 64, 32, 16 }) {
            configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
        }
        return configuration;
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            savePositionToPreferences();
        }));
    }

    /**
     * Saves the position and size to the preferences when we receive the shutdown signal so when we reopen the app
     * we will have it wherever we had.
     */
    private static void savePositionToPreferences() {
        preferences.put("window.size.x", Display.getWidth());
        preferences.put("window.size.y", Display.getHeight());
        preferences.put("window.location.x", Display.getX());
        preferences.put("window.location.y", Display.getY());
    }


}