package xyz.qweru.gruvhack.event.impl;

import xyz.qweru.api.event.Event;
import xyz.qweru.api.event.ImmutableEvent;
import xyz.qweru.api.module.Module;
import xyz.qweru.api.module.ModuleManager;

public class ModuleEvent extends ImmutableEvent {
    private final Module module;
    private final ModuleManager.ToggleType state;

    public ModuleEvent(Module module, ModuleManager.ToggleType state) {
        this.module = module;
        this.state = state;
    }

    public Module getModule() {
        return module;
    }

    public ModuleManager.ToggleType getState() {
        return state;
    }
}
