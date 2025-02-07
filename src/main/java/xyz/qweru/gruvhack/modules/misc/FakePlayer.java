package xyz.qweru.gruvhack.modules.misc;

import xyz.qweru.api.module.Category;
import xyz.qweru.api.module.Module;
import xyz.qweru.api.setting.impl.BooleanSetting;

import static xyz.qweru.gruvhack.Gruvhack.mc;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("FakePlayer", "Show a fake player", Category.MISC);
    }

    BooleanSetting copyInventory = new BooleanSetting("CopyInv", "Copy inventory", true);
    xyz.qweru.api.util.FakePlayer fakePlayer = null;

    @Override
    public void onEnable() {
        if(nullcheck()) return;
        fakePlayer = new xyz.qweru.api.util.FakePlayer("FakePlayer", mc.player.getHealth(), copyInventory.getValue());
        fakePlayer.spawn();
    }

    @Override
    public void onDisable() {
        if(fakePlayer != null) fakePlayer.despawn();
    }
}
