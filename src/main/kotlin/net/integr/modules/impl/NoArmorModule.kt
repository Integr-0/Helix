package net.integr.modules.impl

import net.integr.Helix
import net.integr.event.RenderArmorEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.filters.Filter
import net.integr.modules.management.Module

class NoArmorModule : Module("No Armor", "Hides your Armor", "noArmor", listOf(Filter.Render)) {
    @EventListen
    fun onRenderArmor(event: RenderArmorEvent) {
        if (event.entity == Helix.MC.player) event.cancel()
    }
}