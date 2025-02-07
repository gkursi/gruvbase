package xyz.qweru.api.setting.impl;

import xyz.qweru.api.setting.Setting;

public class TextSetting extends Setting<String> {
    public TextSetting(String name, String description, String value) {
        super(name, description, value);
    }
}
