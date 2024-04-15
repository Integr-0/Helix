package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.client.gui.DrawContext;

public class UiRenderEvent extends Event {
    public DrawContext context;
    public float tickDelta;
    public UiRenderEvent(DrawContext context, float tickDelta) {
        this.context = context;
        this.tickDelta = tickDelta;
    }
}
