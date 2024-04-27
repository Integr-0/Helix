package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.client.world.ClientWorld;

public class JoinWorldEvent extends Event {
    public ClientWorld world;

    public JoinWorldEvent(ClientWorld world) {
        this.world = world;
    }
}
