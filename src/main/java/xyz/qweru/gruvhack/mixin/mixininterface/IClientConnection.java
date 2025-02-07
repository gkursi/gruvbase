package xyz.qweru.gruvhack.mixin.mixininterface;

import net.minecraft.network.packet.Packet;

public interface IClientConnection {
    void gruv$sendImmediately(Packet<?> packet);
}
