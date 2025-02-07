package xyz.qweru.gruvhack.modules;

import xyz.qweru.api.module.Category;
import xyz.qweru.api.module.Module;
import xyz.qweru.api.setting.Setting;
import xyz.qweru.api.setting.impl.BooleanSetting;
import xyz.qweru.api.setting.impl.ModeSetting;
import xyz.qweru.api.setting.impl.NumberSetting;
import xyz.qweru.api.setting.impl.TextSetting;
import xyz.qweru.api.util.InputUtil;

public class TestModule extends Module {
    public TestModule() {
        super("TestModule", "testdescription", Category.CLIENT);
    }

    private final Setting<Mode> mode = new ModeSetting<>("ModeSetting", "module mode", Mode.MODE2);
    private final Setting<Boolean> boool = new BooleanSetting("BoolSetting", "do this?", true);
    private final Setting<String> texttt = new TextSetting("SomeText", "text2spam", "hi");
    private final Setting<Number> range = new NumberSetting("Range", "how far", 10, -50, 50);

    @Override
    public String getState() {
        return ":3";
    }

    enum Mode {
        MODE1,
        MODE2,
        MODE3
    }
}
