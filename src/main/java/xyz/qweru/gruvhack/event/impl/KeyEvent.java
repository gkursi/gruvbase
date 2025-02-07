package xyz.qweru.gruvhack.event.impl;

import xyz.qweru.api.event.Event;
import xyz.qweru.api.event.ImmutableEvent;
import xyz.qweru.api.util.InputUtil;

public class KeyEvent extends ImmutableEvent {

    private final int key;
    private final int scancode;
    private final int action;
    private final int modifiers;
    private final String keyName;

    public KeyEvent(int key, int scancode, int action, int modifiers) {
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.modifiers = modifiers;
        this.keyName = InputUtil.getKey(key);
    }

    public int getKey() {
        return key;
    }

    public int getScancode() {
        return scancode;
    }

    public int getAction() {
        return action;
    }

    public int getModifiers() {
        return modifiers;
    }

    public String getKeyName() {
        return keyName;
    }
}
