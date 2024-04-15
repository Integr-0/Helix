package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;

public class RenderArmorEvent extends Event {
    public MatrixStack matrices;
    public VertexConsumerProvider vertexConsumers;
    public Entity entity;
    public EquipmentSlot armorSlot;
    public int light;
    public BipedEntityModel<?> model;

    public RenderArmorEvent(BipedEntityModel<?> model, int light, EquipmentSlot armorSlot, Entity entity, VertexConsumerProvider vertexConsumers, MatrixStack matrices) {
        this.model           = model;
        this.light           = light;
        this.armorSlot       = armorSlot;
        this.entity          = entity;
        this.vertexConsumers = vertexConsumers;
        this.matrices        = matrices;
    }
}
