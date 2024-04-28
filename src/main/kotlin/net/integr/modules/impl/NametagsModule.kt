package net.integr.modules.impl

import net.integr.Helix
import net.integr.event.EntityHasLabelEvent
import net.integr.event.EntityRenderLabelEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.filters.Filter
import net.integr.modules.management.Module
import net.integr.modules.management.settings.impl.BooleanSetting
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import kotlin.math.roundToInt

class NametagsModule : Module("Nametags", "Changes the nametags", "nametags", listOf(Filter.Render, Filter.Tweak)) {
    init {
        settings.add(BooleanSetting("Render Own", "Render your own Nametag", "own"))
        settings.add(BooleanSetting("Healthtags", "Render healthtags", "healthTags"))
    }

    @EventListen
    fun onEntityHasLabel(event: EntityHasLabelEvent) {
        if (event.entity == Helix.MC.player && settings.getById<BooleanSetting>("own")!!.isEnabled()) {
            event.callback = MinecraftClient.isHudEnabled()
        }
    }

    @EventListen
    fun onEntityRenderLabel(event: EntityRenderLabelEvent) {
        if (settings.getById<BooleanSetting>("healthTags")!!.isEnabled() && event.entity is PlayerEntity && event.text.string.contains((event.entity as PlayerEntity).gameProfile.name)) {
            event.callback = event.text.copy().append(Text.literal("" + Formatting.RED + " ${(event.entity as LivingEntity).health.roundToInt() + (event.entity as LivingEntity).absorptionAmount.roundToInt()}❤"+ Formatting.GRAY + " ${(event.entity as LivingEntity).distanceTo(Helix.MC.player).roundToInt()}m"))
        }
    }
}