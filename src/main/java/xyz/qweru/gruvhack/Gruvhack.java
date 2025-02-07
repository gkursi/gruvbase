package xyz.qweru.gruvhack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import xyz.qweru.api.ApiMain;
import xyz.qweru.api.config.ConfigManager;
import xyz.qweru.api.logging.Logger;
import xyz.qweru.api.module.ModuleManager;
import xyz.qweru.api.render.font.Font;
import xyz.qweru.gruvhack.mixin.mixininterface.ISession;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class Gruvhack implements ClientModInitializer {

    // fuck you im using my own logger
    public static Logger LOGGER = new Logger("Gruvhack", true);
    public static MinecraftClient mc = MinecraftClient.getInstance();
    public static Path CONFIG_PATH = FabricLoader.getInstance().getGameDir().resolve("Gruvhack");
    public static ConfigManager CONFIG = ConfigManager.create(CONFIG_PATH);
    public static final String GIT_HASH = getGitCommitHash();
    public static final boolean DEV_ENV = mc.getSession().getAccessToken().equals("FabricMC");

    private static final Timer CONFIG_TIMER = new Timer("Config timer");
    @Override
    public void onInitializeClient() {
        ApiMain.init();
        LOGGER.info("Starting Gruvhack");

        // config
        CONFIG.addConfig(ModuleManager.INSTANCE.getConfig());
        CONFIG.load();
        CONFIG_TIMER.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                CONFIG.save();
            }
        }, 10000, 10000);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> CONFIG.save()));
        LOGGER.info("Loaded config");

        // visuals
        Font.init();
        LOGGER.info("Loaded font");
        LOGGER.info("Finished starting Gruvhack");
        if(DEV_ENV) ((ISession) mc.getSession()).gruv$setUser("GruvOnTop");
    }

    public static String getGitCommitHash() {
        String base = DEV_ENV ? "dev-" : "";

        Properties properties = new Properties();
        try (InputStream inputStream = Gruvhack.class.getResourceAsStream("/git.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
                return base + properties.getProperty("git.commit.id.abbrev");
            } else {
                return base + "unknown";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return base + "unknown";
        }
    }
}
