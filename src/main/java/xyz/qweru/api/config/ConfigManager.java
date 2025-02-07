package xyz.qweru.api.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static xyz.qweru.gruvhack.Gruvhack.CONFIG;
import static xyz.qweru.gruvhack.Gruvhack.LOGGER;

public class ConfigManager {
    private static final int CONFIG_SCHEMA_VERSION = 3;
    public int loadedVersion = -1;

    final Path root;
    private final ArrayList<ConfigFile> configs = new ArrayList<>();
    public static ConfigManager create(Path root) {
        ConfigManager configManager = new ConfigManager(root);
        ConfigFile metadata = new ConfigFile("qc-metadata", new Jsonable() {
            @Override
            public void addJson(JsonObject root) {
                root.addProperty("VERSION", CONFIG_SCHEMA_VERSION);
            }

            @Override
            public void readJson(JsonObject json) {
                int version = json.get("VERSION").getAsInt();
                if(version != CONFIG_SCHEMA_VERSION) {
                    LOGGER.warn("Config is {} than client! (config version: {}, client config version: {})", version > CONFIG_SCHEMA_VERSION ? "newer" : "older", version, CONFIG_SCHEMA_VERSION);
                }
                configManager.loadedVersion = version;
            }
        });
        configManager.addConfig(metadata);
        return configManager;
    }

    private ConfigManager(Path root) {
        this.root = root;
    }

    public void addConfig(ConfigFile path) {
        configs.add(path);
    }

    public void load() {
        for (ConfigFile config : configs) {
            config.read(root);
        }
    }

    public void save() {
        File rootDir = root.toFile();
        if(!rootDir.exists()) rootDir.mkdir();
        for (ConfigFile config : configs) {
            config.write(root);
        }
    }

    public static class ConfigFile {
        private final String name;
        private final ArrayList<Jsonable> children;

        public ConfigFile(String name, Jsonable... children) {
            this(name, List.of(children));
        }

        public ConfigFile(String name, List<Jsonable> children) {
            this.name = name + (name.endsWith(".json") ? "" : ".json");
            this.children = new ArrayList<>(children);
        }

        public void write(Path root) {
            Path path = root.resolve(name);
            JsonObject obj = new JsonObject();
            for (Jsonable child : children) {
                child.addJson(obj);
            }
            try(FileWriter writer = new FileWriter(path.toFile())){
                writer.write(obj.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void read(Path root) {
            File file = root.resolve(name).toFile();
            if(!file.exists() || !root.toFile().exists()) return;
            try {
                JsonObject obj = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
                for (Jsonable child : children) child.readJson(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
