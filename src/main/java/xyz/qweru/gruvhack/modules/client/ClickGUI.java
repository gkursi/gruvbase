package xyz.qweru.gruvhack.modules.client;

import org.lwjgl.glfw.GLFW;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.KeyEvent;
import xyz.qweru.api.module.Category;
import xyz.qweru.api.module.Module;
import xyz.qweru.api.util.InputUtil;

import static xyz.qweru.gruvhack.Gruvhack.mc;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", "the gui", Category.CLIENT);
        Events.KEY.subscribe(this::onKey);
    }

    void onKey(KeyEvent e) {
//        if(e.getKey() == InputUtil.KEY_RIGHT_SHIFT && e.getAction() == GLFW.GLFW_PRESS) mc.setScreen(mc.currentScreen instanceof GuiScreen ? null : new GuiScreen());
    }
}
