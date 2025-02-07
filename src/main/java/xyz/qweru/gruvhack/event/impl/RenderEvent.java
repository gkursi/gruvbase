package xyz.qweru.gruvhack.event.impl;

import net.minecraft.client.util.math.MatrixStack;
import xyz.qweru.api.event.Event;
import xyz.qweru.api.event.ImmutableEvent;

public class RenderEvent extends ImmutableEvent {

    private final MatrixStack matrices;
    private final float td;

    public RenderEvent(MatrixStack matrices, float td) {
        this.matrices = matrices;
        this.td = td;
    }

    public MatrixStack getMatrices() {
        return matrices;
    }

    public float getTd() {
        return td;
    }
}
