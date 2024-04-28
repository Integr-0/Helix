package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockBreakEvent extends Event {
    BlockPos pos;
    Direction direction;

    public BlockBreakEvent(BlockPos pos, Direction direction) {
        this.pos       = pos;
        this.direction = direction;
    }
}
