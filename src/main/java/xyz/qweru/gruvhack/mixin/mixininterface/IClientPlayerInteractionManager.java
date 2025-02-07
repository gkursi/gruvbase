package xyz.qweru.gruvhack.mixin.mixininterface;

import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;

public interface IClientPlayerInteractionManager {
    void gruv$sendSequencedPacket(ClientWorld world, SequencedPacketCreator packetCreator);
    void gruv$sendSequencedPacket(Packet<ServerPlayPacketListener> packet);
}
