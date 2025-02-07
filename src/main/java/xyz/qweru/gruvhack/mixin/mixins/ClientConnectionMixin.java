package xyz.qweru.gruvhack.mixin.mixins;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.PacketEvent;
import xyz.qweru.gruvhack.mixin.mixininterface.IClientConnection;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin<T extends PacketListener> implements IClientConnection {

    @Shadow protected abstract void sendImmediately(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush);

    @Inject(method = "sendInternal", at = @At("HEAD"), cancellable = true)
    void sendPacket(Packet<?> packet, PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        if(Events.PACKET_SEND.call(new PacketEvent(packet)).isCancelled()) ci.cancel();
    }

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    void handlePacket(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        if(Events.PACKET_RECIEVE.call(new PacketEvent(packet)).isCancelled()) ci.cancel();
    }

    @Override
    public void gruv$sendImmediately(Packet<?> packet) {
        this.sendImmediately(packet, null, true);
    }
}
