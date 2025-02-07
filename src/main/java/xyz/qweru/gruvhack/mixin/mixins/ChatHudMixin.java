package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import xyz.qweru.gruvhack.mixin.mixininterface.IChatHud;

import java.util.List;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin implements IChatHud {
    @Shadow @Final private List<ChatHudLine> messages;

    @Shadow public abstract void addMessage(Text message);

    @Override
    public void gruv$add(String text) {
        this.addMessage(Text.of(text));
    }
}
