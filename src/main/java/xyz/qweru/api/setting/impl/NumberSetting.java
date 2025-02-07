package xyz.qweru.api.setting.impl;

import xyz.qweru.api.setting.Setting;

// todo: round to digits setting
public class NumberSetting extends Setting<Number> {
    private final Number min;
    private final Number max;
    private final int roundToDigits;

    public NumberSetting(String name, String description, Number value, Number min, Number max, int roundToDigits) {
        super(name, description, value);
        this.min = min;
        this.max = max;
        this.roundToDigits = roundToDigits;
    }

    public NumberSetting(String name, String description, Number value, Number min, Number max) {
        this(name, description, value, min, max, 2);
    }

    @Override
    public void setValue(Number value) {
        super.setValue(clamp((roundToDigits == -1 ? value : round(value.doubleValue(), roundToDigits)), min, max));
    }

    static Number clamp(Number val, Number min, Number max) {
        return Math.min(Math.max(min.doubleValue(), val.doubleValue()), max.doubleValue());
    }

    static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public Number getMin() {
        return min;
    }

    public Number getMax() {
        return max;
    }
}
