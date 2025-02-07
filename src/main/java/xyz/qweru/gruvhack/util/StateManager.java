package xyz.qweru.gruvhack.util;

import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.network.packet.s2c.query.PingResultS2CPacket;
import xyz.qweru.api.module.Module;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.GenericEvent;
import xyz.qweru.gruvhack.event.impl.PacketEvent;

import static xyz.qweru.gruvhack.Gruvhack.LOGGER;
import static xyz.qweru.gruvhack.Gruvhack.mc;

// todo: PopCounter
public class StateManager {
    private StateManager() {}
    static {
        Events.PACKET_RECIEVE.subscribe(StateManager::onClientBound);
        Events.PACKET_SEND.subscribe(StateManager::onServerBound);
        Events.POST_TICK.subscribe(StateManager::postTick);
    }

    private static int serverSlot = -1;
    public static int getServerSlot() {
        return serverSlot;
    }

    private static void onClientBound(PacketEvent e) {
        if(e.getPacket() instanceof UpdateSelectedSlotS2CPacket(int slot)) {
            serverSlot = slot;
        }
    }

    private static void onServerBound(PacketEvent e) {
        if(e.getPacket() instanceof UpdateSelectedSlotC2SPacket p) {
            serverSlot = p.getSelectedSlot();
        }
    }

    private static int ping = -1;
    public static int getPing() {
        return ping;
    }

    private static int counter = 0;
    private static void postTick(GenericEvent ignored) {
        if(Module.nullcheck()) return;
        if(counter == 0) {
            counter = 20;
            try {
                ping = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()).getLatency();
            } catch (NullPointerException e) {
                LOGGER.warn("Error when updating ping: NullPointerException");
            }
        }
    }
}
