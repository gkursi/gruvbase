package xyz.qweru.gruvhack.event.impl;

import net.minecraft.network.packet.Packet;
import xyz.qweru.api.event.Event;

public class PacketEvent extends Event {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
