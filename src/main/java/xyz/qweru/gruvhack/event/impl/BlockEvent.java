package xyz.qweru.gruvhack.event.impl;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import xyz.qweru.api.event.Event;

public class BlockEvent extends Event {
    private final BlockPos pos;
    private final BlockState state;
    private final Direction direction;

    public BlockEvent(BlockPos pos, BlockState state, Direction direction) {
        this.pos = pos;
        this.state = state;
        this.direction = direction;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }

    public Direction getDirection() {
        return direction;
    }
}
