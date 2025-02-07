package xyz.qweru.api.setting.impl;

import xyz.qweru.api.setting.Setting;
import xyz.qweru.gruvhack.Gruvhack;

import java.util.ArrayList;
import java.util.List;

public class ModeSetting<T extends Enum<?>> extends Setting<T> {

    final T[] values;
    int index = -1;
    @SuppressWarnings("unchecked")
    public ModeSetting(String name, String description, T value) {
        super(name, description, value);
        values = ((T[]) value.getDeclaringClass().getEnumConstants());
        setValue(value);
    }

    @Override
    public void setValue(T value) {
        int i = 0;
        for (T t : values) {
            if (t == value) {
                index = i;
                break;
            }
            i++;
        }
        super.setValue(value);
    }

    public void cycle() {
        index++;
        if(index >= values.length) {
            index = 0;
        }
        value = values[index];
    }

    ArrayList<String> strings = null;
    public ArrayList<String> getStrings() {
        if(strings == null || strings.isEmpty()) {
            strings = new ArrayList<>();
            for (T t : values) {
                strings.add(t.name());
            }
        }
        return strings;
    }

    public void setIndex(int i) {
        index = i;
        setValue(values[i]);
    }
}
