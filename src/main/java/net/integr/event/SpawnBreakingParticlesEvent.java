package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpawnBreakingParticlesEvent extends Event {
    public World world;
    public PlayerEntity player;
    public BlockPos pos;
    public BlockState state;

    public SpawnBreakingParticlesEvent(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        this.world  = world;
        this.player = player;
        this.pos    = pos;
        this.state  = state;
    }
}
