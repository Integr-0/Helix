package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.client.util.math.MatrixStack;

public class RenderWorldEvent extends Event {
    public float tickDelta;
    public MatrixStack matrices;
    public RenderWorldEvent(float tickDelta, MatrixStack matrices) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }
}
