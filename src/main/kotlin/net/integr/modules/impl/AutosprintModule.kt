package net.integr.modules.impl

import net.integr.Helix
import net.integr.event.PreTickEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.management.Module

class AutosprintModule : Module("Autosprint", "Sprints for you", "autoSprint") {
    @EventListen
    fun onPreTick(event: PreTickEvent) {
        if (!Helix.MC.options.sprintKey.isPressed) Helix.MC.options.sprintKey.isPressed = true
    }
}