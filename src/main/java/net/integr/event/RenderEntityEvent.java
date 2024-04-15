package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class RenderEntityEvent extends Event {
    public Entity entity;
    public double cameraX;
    public double cameraY;
    public double cameraZ;
    public float tickDelta;
    public MatrixStack matrices;
    public VertexConsumerProvider vertexConsumers;

    public RenderEntityEvent(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
        this.entity          = entity;
        this.cameraX         = cameraX;
        this.cameraY         = cameraY;
        this.cameraZ         = cameraZ;
        this.tickDelta       = tickDelta;
        this.matrices        = matrices;
        this.vertexConsumers = vertexConsumers;
    }
}
