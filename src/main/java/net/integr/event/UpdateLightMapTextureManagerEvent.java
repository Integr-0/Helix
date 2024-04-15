package net.integr.event;

import net.integr.eventsystem.Event;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

public class UpdateLightMapTextureManagerEvent extends Event {
    public Args args;

    public UpdateLightMapTextureManagerEvent(Args args) {
        this.args = args;
    }
}
