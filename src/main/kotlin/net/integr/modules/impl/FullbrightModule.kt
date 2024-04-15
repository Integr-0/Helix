package net.integr.modules.impl

import net.integr.event.UpdateLightMapTextureManagerEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.management.Module

class FullbrightModule : Module("Fullbright", "Makes you see in the dark", "fullbright") {
    @EventListen
    fun onUpdateLightMapTextureManager(event: UpdateLightMapTextureManagerEvent) {
        event.args.set(2, -0x1)
    }
}