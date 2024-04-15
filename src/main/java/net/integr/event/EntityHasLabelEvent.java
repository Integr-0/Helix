package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.entity.LivingEntity;

public class EntityHasLabelEvent extends Event {
    public LivingEntity entity;

    public EntityHasLabelEvent(LivingEntity entity) {
        this.entity = entity;
    }
}
