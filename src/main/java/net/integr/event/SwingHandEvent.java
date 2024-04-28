package net.integr.event;

import net.integr.eventsystem.Event;
import net.minecraft.util.Hand;

public class SwingHandEvent extends Event {
    Hand hand;

    public SwingHandEvent(Hand hand) {
        this.hand = hand;
    }
}
