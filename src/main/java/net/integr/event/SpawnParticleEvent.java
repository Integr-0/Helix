package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.particle.ParticleEffect;

public class SpawnParticleEvent extends Event {
    public ParticleEffect parameters;
    public boolean alwaysSpawn;
    public boolean canSpawnOnMinimal;
    public double x;
    public double y;
    public double z;
    public double velocityX;
    public double velocityY;
    public double velocityZ;

    public SpawnParticleEvent(ParticleEffect parameters, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this.parameters        = parameters;
        this.alwaysSpawn       = alwaysSpawn;
        this.canSpawnOnMinimal = canSpawnOnMinimal;
        this.x                 = x;
        this.y                 = y;
        this.z                 = z;
        this.velocityX         = velocityX;
        this.velocityY         = velocityY;
        this.velocityZ         = velocityZ;
    }
}
