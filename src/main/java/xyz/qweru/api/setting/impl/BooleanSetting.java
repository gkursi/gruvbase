package xyz.qweru.api.setting.impl;

import xyz.qweru.api.setting.Setting;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name, String description, Boolean value) {
        super(name, description, value);
    }

    @Override
    public Boolean getValue() {
        return super.getValue() && getVisibility(); // only return true if visible
    }
}
