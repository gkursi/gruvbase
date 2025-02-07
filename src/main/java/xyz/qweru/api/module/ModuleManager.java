package xyz.qweru.api.module;

import org.lwjgl.glfw.GLFW;
import xyz.qweru.api.config.ConfigManager;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.KeyEvent;
import xyz.qweru.gruvhack.event.impl.ModuleEvent;
import xyz.qweru.api.util.Manager;
import xyz.qweru.gruvhack.Gruvhack;
import xyz.qweru.gruvhack.modules.misc.FakePlayer;

import java.util.ArrayList;

import static xyz.qweru.api.ApiMain.mc;

public class ModuleManager extends Manager<Module> {
    public static ModuleManager INSTANCE = new ModuleManager();
    private ModuleManager() {
        Events.KEY.subscribe(this::onKey);
        // add your module here
        add(new FakePlayer());
        // todo: load via reflection
        Gruvhack.LOGGER.info("Loaded modules");
    }

    @Override
    public void add(Module item) {
        Module.updateSettings(item);
        super.add(item);
    }

    public ConfigManager.ConfigFile getConfig() {
        return new ConfigManager.ConfigFile("modules", new ArrayList<>(getAll()));
    }

    void onKey(KeyEvent e) {
        for (Module module : getAll()) {
            if(module.getBind() == e.getKey() && e.getKey() != -1 && e.getAction() == GLFW.GLFW_PRESS && mc.currentScreen == null) {
                boolean prevState = module.isEnabled();
                module.toggle();
                ToggleType type;
                if(!prevState && !module.isEnabled()) { // if module was disabled before and is disabled after enabling
                    type = ToggleType.RAN;
                } else type = module.isEnabled() ? ToggleType.ENABLED : ToggleType.DISABLED;
                Events.MODULE_STATE_CHANGE.call(new ModuleEvent(module, type));
            }
        }
    }

    public enum ToggleType {
        ENABLED,
        DISABLED,
        RAN
    }
}
