package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.network.packet.Packet;

public class SendPacketEvent extends Event {
    public Packet<?> packet;

    public SendPacketEvent(Packet<?> packet) {
        this.packet = packet;
    }
}
