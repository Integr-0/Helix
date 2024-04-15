package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class RenderArmEvent extends Event {
    public AbstractClientPlayerEntity player;
    public float tickDelta;
    public float pitch;
    public Hand hand;
    public float swingProgress;
    public ItemStack item;
    public float equipProgress;
    public MatrixStack matrices;
    public VertexConsumerProvider vertexConsumers;
    public int light;

    public RenderArmEvent(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        this.player          = player;
        this.tickDelta       = tickDelta;
        this.pitch           = pitch;
        this.hand            = hand;
        this.swingProgress   = swingProgress;
        this.item            = item;
        this.equipProgress   = equipProgress;
        this.matrices        = matrices;
        this.vertexConsumers = vertexConsumers;
        this.light           = light;
    }
}
