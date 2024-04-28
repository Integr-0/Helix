package net.integr.modules.impl

import net.integr.event.RenderEntityEvent
import net.integr.event.SpawnBreakingParticlesEvent
import net.integr.event.SpawnParticleEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.filters.Filter
import net.integr.modules.management.Module
import net.integr.modules.management.settings.impl.BooleanSetting
import net.minecraft.entity.EntityType
import net.minecraft.particle.ParticleTypes

class NoRenderModule : Module("NoRender", "Disables rendering of some things", "noRender", listOf(Filter.Render, Filter.Tweak)) {
    init {
        settings
            .add(BooleanSetting("No Items", "Disable Items", "items"))
            .add(BooleanSetting("No Entities", "Disable Entities", "entities"))
            .add(BooleanSetting("No Break Particles", "Disable Break Particles", "breakParticles"))
            .add(BooleanSetting("No Explosions", "Disable Explosions", "explosions"))
    }

    @EventListen
    fun onRenderEntity(event: RenderEntityEvent) {
        if (settings.getById<BooleanSetting>("entities")!!.isEnabled()) {
            event.cancel()
        } else {
            if (settings.getById<BooleanSetting>("items")!!.isEnabled()) {
                if (event.entity.type == EntityType.ITEM) {
                    event.cancel()
                }
            }
        }
    }

    @EventListen
    fun onSpawnParticle(event: SpawnParticleEvent) {
        if (event.parameters.type == ParticleTypes.EXPLOSION || event.parameters.type == ParticleTypes.EXPLOSION_EMITTER) {
            if (settings.getById<BooleanSetting>("explosions")!!.isEnabled()) {
                event.cancel()
            }
        }
    }

    @EventListen
    fun onSpawnBreakingParticles(event: SpawnBreakingParticlesEvent) {
        if (settings.getById<BooleanSetting>("breakParticles")!!.isEnabled()) {
            event.cancel()
        }
    }
}