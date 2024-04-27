package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.network.packet.Packet;

public class ReceivePacketEvent extends Event {
    public Packet<?> packet;

    public ReceivePacketEvent(Packet<?> packet) {
        this.packet = packet;
    }
}
