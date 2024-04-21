package net.integr.event;

import net.integr.eventsystem.Event;

public class SendChatMessageEvent extends Event {
    public String message;

    public SendChatMessageEvent(String message) {
        this.message = message;
    }
}
