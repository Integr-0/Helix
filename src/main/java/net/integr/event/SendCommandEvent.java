package net.integr.event;

import net.integr.eventsystem.Event;

public class SendCommandEvent extends Event {
    public String command;

    public SendCommandEvent(String command) {
        this.command = command;
    }
}
