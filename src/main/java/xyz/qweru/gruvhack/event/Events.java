package xyz.qweru.gruvhack.event;

import xyz.qweru.api.event.EventManager;
import xyz.qweru.gruvhack.event.impl.*;

public class Events {
    private Events() {}
    public static EventManager<GenericEvent> PRE_TICK = new EventManager<>();
    public static EventManager<GenericEvent> POST_TICK = new EventManager<>();
    public static EventManager<KeyEvent> KEY = new EventManager<>();
    public static EventManager<ModuleEvent> MODULE_STATE_CHANGE = new EventManager<>();
    public static EventManager<RotationEvent> ROTATION_UPDATE = new EventManager<>();
    public static EventManager<MoveEvent> POSITION_UPDATE = new EventManager<>();
    public static EventManager<VelocityUpdateEvent> VELOCITY_UPDATE = new EventManager<>();
    public static EventManager<BlockEvent> BLOCK_ATTACK = new EventManager<>();
    public static EventManager<PacketEvent> PACKET_SEND = new EventManager<>();
    public static EventManager<PacketEvent> PACKET_RECIEVE = new EventManager<>();
    public static EventManager<RenderEvent> RENDER_3D = new EventManager<>();
}

