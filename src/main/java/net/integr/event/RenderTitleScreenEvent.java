package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.client.gui.DrawContext;

public class RenderTitleScreenEvent extends Event {
    public DrawContext context;
    public int mouseX;
    public int mouseY;
    public float delta;

    public RenderTitleScreenEvent(DrawContext context, int mouseX, int mouseY, float delta) {
        this.context = context;
        this.mouseX  = mouseX;
        this.mouseY  = mouseY;
        this.delta   = delta;
    }
}
