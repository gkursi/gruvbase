package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.MoveEvent;
import xyz.qweru.gruvhack.event.impl.RotationEvent;
import xyz.qweru.gruvhack.event.impl.VelocityUpdateEvent;

import java.util.ArrayList;
import java.util.List;

import static xyz.qweru.gruvhack.Gruvhack.mc;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Shadow @Final protected MinecraftClient client;

    @Shadow protected abstract void sendSprintingPacket();

    @Shadow protected abstract boolean isCamera();

    @Shadow private int ticksSinceLastPositionPacketSent;

    @Shadow private boolean lastOnGround;

    @Shadow private boolean lastHorizontalCollision;

    @Shadow private boolean autoJumpEnabled;

    @Shadow private double lastX;

    @Shadow private double lastBaseY;

    @Shadow private double lastZ;

    @Shadow private float lastYaw;

    @Shadow private float lastPitch;

    @Shadow @Final public ClientPlayNetworkHandler networkHandler;

    @Unique private List<Object> objects = new ArrayList<>();
    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    void sendMovementPackets(CallbackInfo ci) {
        // todo cleanup
        objects.clear();
        RotationEvent rot = Events.ROTATION_UPDATE.call(new RotationEvent(mc.player.getYaw(), mc.player.getPitch()));
        MoveEvent move = Events.POSITION_UPDATE.call(new MoveEvent(mc.player.getX(), mc.player.getY(), mc.player.getZ(), mc.player.isOnGround(), mc.player.horizontalCollision));
        objects.add(mc.player.getPitch());
        objects.add(mc.player.getYaw());
        objects.add(mc.player.getPos());
        objects.add(mc.player.isOnGround());
        objects.add(mc.player.horizontalCollision);
        mc.player.setPitch(rot.getPitch());
        mc.player.setYaw(rot.getYaw());
        mc.player.setPos(move.getX(), move.getY(), move.getZ());
        mc.player.setOnGround(move.isOnGround());
        mc.player.horizontalCollision = move.horizontalCollision;
    }

    @Inject(method = "sendMovementPackets", at = @At("TAIL"))
    void postSend(CallbackInfo ci) {
        if(this.isCamera() && !objects.isEmpty()) {
            mc.player.setPitch(((float) objects.get(0)));
            mc.player.setYaw((float) objects.get(1));
            Vec3d pos = (Vec3d) objects.get(2);
            mc.player.setPos(pos.x, pos.y, pos.z);
            mc.player.setOnGround((boolean) objects.get(3));
            mc.player.horizontalCollision = (boolean) objects.get(4);
        }
    }

    @ModifyArgs(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void setVelocity(Args args) {
        Vec3d original = args.get(0);
        VelocityUpdateEvent event = Events.VELOCITY_UPDATE.call(new VelocityUpdateEvent(original.x, original.y, original.z));
        args.set(0, new Vec3d(event.getX(), event.getY(), event.getZ()));
    }
}

