package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.qweru.api.module.HudModule;
import xyz.qweru.api.module.Module;
import xyz.qweru.api.module.ModuleManager;
import xyz.qweru.gruvhack.Gruvhack;

import java.awt.*;

import static xyz.qweru.gruvhack.Gruvhack.mc;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        context.drawText(mc.textRenderer, "Gruvhack v0.0.1-" + Gruvhack.GIT_HASH, 2, 2, Color.decode("#ebdbb2").getRGB(), true);
        for (Module module : ModuleManager.INSTANCE.getAll()) {
            if(module instanceof HudModule hm && hm.isEnabled()) {
                hm.render(context, tickCounter.getTickDelta(true));
            }
        }
    }

}
