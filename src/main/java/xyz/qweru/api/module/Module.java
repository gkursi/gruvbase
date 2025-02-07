package xyz.qweru.api.module;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.qweru.api.config.Jsonable;
import xyz.qweru.api.setting.Setting;
import xyz.qweru.api.setting.impl.BooleanSetting;
import xyz.qweru.api.setting.impl.ModeSetting;
import xyz.qweru.api.setting.impl.NumberSetting;
import xyz.qweru.api.setting.impl.TextSetting;
import xyz.qweru.api.util.InputUtil;
import xyz.qweru.gruvhack.Gruvhack;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static xyz.qweru.api.ApiMain.mc;

public abstract class Module implements Jsonable {

    private final String name;
    private final String description;
    private final Category category;
    private final ArrayList<Setting<?>> settings = new ArrayList<>();
    private int bind = InputUtil.KEY_UNKNOWN;
    private boolean enabled = false;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public static void updateSettings(Module obj) {
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (!Setting.class.isAssignableFrom(field.getType())) continue;
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                obj.settings.add((Setting<?>)field.get(obj));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private long toggleMS = -1;
    public void setEnabled(boolean enabled) {
        boolean prev = this.enabled;
        this.enabled = enabled;
        if(prev == enabled) return;
        toggleMS = System.currentTimeMillis();
        if(enabled) onEnable();
        else onDisable();
    }

    public long getToggleMS() {
        return toggleMS;
    }

    public void onEnable() {}
    public void onDisable() {}

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void toggle() {
        this.setEnabled(!enabled);
    }

    public ArrayList<Setting<?>> getSettings() {
        return settings;
    }

    @Override
    public void addJson(JsonObject root) {
        JsonObject object = new JsonObject();
        object.addProperty("bind", this.bind);
        object.addProperty("state", this.enabled);

        JsonArray settings = new JsonArray();
        for (Setting<?> setting : this.settings) {
            JsonObject settingObj = new JsonObject();
            settingObj.addProperty("name", setting.getName());
            settingObj.addProperty("value", setting.getValue().toString());
            settings.add(settingObj);
        }

        object.add("settings", settings);
        root.add(this.getName(), object);
    }

    @Override
    public void readJson(JsonObject json) {
        JsonObject object = json.getAsJsonObject(this.getName());
        if(object == null) {
            Gruvhack.LOGGER.warn("Skipping {} (not in config)", this.getName());
            return;
        }

        this.setBind(object.get("bind").getAsInt());
        this.setEnabled(object.get("state").getAsBoolean());
        JsonArray settings = object.get("settings").getAsJsonArray();
        for (JsonElement st : settings) {
            JsonObject setting = st.getAsJsonObject();
            String name = setting.get("name").getAsString();
            String value = setting.get("value").getAsString();
            for (Setting<?> realSetting : this.settings) {
                if(realSetting.getName().equalsIgnoreCase(name)) {
                    switch (realSetting) {
                        case ModeSetting<?> modeSetting -> {
                            int i = 0;
                            for (String string : modeSetting.getStrings()) {
                                if (string.equalsIgnoreCase(value)) modeSetting.setIndex(i);
                                i++;
                            }
                        }
                        case BooleanSetting booleanSetting -> booleanSetting.setValue(Boolean.valueOf(value));
                        case TextSetting textSetting -> textSetting.setValue(value);
                        case NumberSetting numberSetting -> numberSetting.setValue(Double.valueOf(value));
                        default -> throw new RuntimeException("Unknown setting type! (name: " + realSetting.getName() + ")");
                    }
                    break;
                }
            }
        }
    }

    public static boolean nullcheck() {
        return mc.world == null || mc.player == null;
    }

    /**
     * For arraylist
     */
    public String getState() {
        return "";
    }
}
