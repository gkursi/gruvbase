package xyz.qweru.api.setting;

import xyz.qweru.api.setting.impl.BooleanSetting;

import java.util.function.Supplier;

public abstract class Setting<T> {
    private final String name;
    private final String description;
    protected T value;
    private Supplier<Boolean> getVisibility = () -> true;

    public Setting(String name, String description, T value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean getVisibility() {
        return getVisibility.get();
    }

    public void setVisibilitySupplier(Supplier<Boolean> getVisibility) {
        this.getVisibility = getVisibility;
    }

    public void setVisibilitySupplier(BooleanSetting setting) {
        this.getVisibility = setting::getValue;
    }
}
