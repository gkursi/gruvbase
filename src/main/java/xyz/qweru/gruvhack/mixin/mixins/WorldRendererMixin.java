package xyz.qweru.gruvhack.mixin.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.*;
import net.minecraft.client.util.Handle;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.profiler.Profiler;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.RenderEvent;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Unique
    private MatrixStack nextStack = null;

    @Inject(
            method = {"method_62214"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/debug/DebugRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/Frustum;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;DDD)V",
                    ordinal = 0
            )}
    )
    void beforeClouds(Fog fog, RenderTickCounter renderTickCounter, Camera camera, Profiler profiler, Matrix4f matrix4f, Matrix4f matrix4f2, Handle handle, Handle handle2, Handle handle3, Handle handle4, boolean bl, Frustum frustum, Handle handle5, CallbackInfo ci) {
        if(nextStack != null) Events.RENDER_3D.call(new RenderEvent(nextStack, renderTickCounter.getTickDelta(true)));
    }

    @ModifyExpressionValue(method = "method_62214", at = @At(value = "NEW", target = "net/minecraft/client/util/math/MatrixStack"))
    MatrixStack createStack(MatrixStack original) {
        this.nextStack = original;
        return original;
    }
}
