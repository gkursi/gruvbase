package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.KeyEvent;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if(window == this.client.getWindow().getHandle()) {
            if(Events.KEY.call(new KeyEvent(key, scancode, action, modifiers)).isCancelled()) ci.cancel();
        }
    }

}
