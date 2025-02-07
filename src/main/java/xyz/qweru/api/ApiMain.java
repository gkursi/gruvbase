package xyz.qweru.api;

import net.minecraft.client.MinecraftClient;
import xyz.qweru.api.logging.StreamManager;

public class ApiMain {
    private static final StreamManager outManager = new StreamManager();
    private static final StreamManager errManager = new StreamManager();
    public static final MinecraftClient mc = MinecraftClient.getInstance();

    static boolean initialized = false;
    public static void init() {
        if(initialized) return;
        initialized = true;
        // log4j logging is ugly, so instead of learning how to change it I made my own
        outManager.capture(StreamManager.Stream.STDOUT);
        errManager.capture(StreamManager.Stream.STDERR);
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
