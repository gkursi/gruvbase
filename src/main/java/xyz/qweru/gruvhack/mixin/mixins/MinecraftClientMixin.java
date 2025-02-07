package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.GenericEvent;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    void tick(CallbackInfo ci) {
        Events.PRE_TICK.call(new GenericEvent());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    void postTick(CallbackInfo ci) {
        Events.POST_TICK.call(new GenericEvent());
    }
}
