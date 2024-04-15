package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

public class EntityRenderLabelEvent extends Event {
    public Entity entity;
    public Text text;
    public MatrixStack matrices;
    public VertexConsumerProvider vertexConsumers;
    public int light;

    public EntityRenderLabelEvent(Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        this.entity          = entity;
        this.text            = text;
        this.matrices        = matrices;
        this.vertexConsumers = vertexConsumers;
        this.light           = light;
    }
}
