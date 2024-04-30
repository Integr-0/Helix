package net.integr.modules.impl

import net.integr.Helix
import net.integr.Variables
import net.integr.event.RenderWorldEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.filters.Filter
import net.integr.modules.management.Module
import net.integr.modules.management.settings.impl.BooleanSetting
import net.integr.modules.management.settings.impl.CyclerSetting
import net.integr.rendering.RenderingEngine
import net.integr.utilities.CoordinateUtils
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult

class BlockOutlineModule : Module("Block Outline", "Changes your block outline", "blockOutline", listOf(Filter.Render)) {
    init {
        settings
            .add(CyclerSetting("Mode: ", "The Mode to use", "mode", mutableListOf("Full", "Lines")))
            .add(BooleanSetting("Entity Box", "Also render a box around the entity you are looking at", "entityBox"))
    }

    @EventListen
    fun onRender(event: RenderWorldEvent) {
        val t = Helix.MC.crosshairTarget

        if (t?.type == HitResult.Type.BLOCK) {
            when (settings.getById<CyclerSetting>("mode")!!.getElement()) {
                "Full" -> {
                    RenderingEngine.ThreeDimensional.box((t as BlockHitResult).blockPos.toCenterPos().add(0.0, -0.5, 0.0), event.matrices, Variables.guiColor)
                }

                "Lines" -> {
                    RenderingEngine.ThreeDimensional.outlinedBox((t as BlockHitResult).blockPos.toCenterPos().add(0.0, -0.5, 0.0), event.matrices, Variables.guiColor)
                }
            }
        } else if (t?.type == HitResult.Type.ENTITY && settings.getById<BooleanSetting>("entityBox")!!.isEnabled()) {
            val te = t as EntityHitResult

            if (!te.entity.isInvisible) {
                when (settings.getById<CyclerSetting>("mode")!!.getElement()) {
                    "Full" -> {
                        RenderingEngine.ThreeDimensional.box(CoordinateUtils.getEntityBox(te.entity), CoordinateUtils.getLerpedEntityPos(te.entity, event.tickDelta), event.matrices, Variables.guiColor)
                    }

                    "Lines" -> {
                        RenderingEngine.ThreeDimensional.outlinedBox(CoordinateUtils.getEntityBox(te.entity), CoordinateUtils.getLerpedEntityPos(te.entity, event.tickDelta), event.matrices, Variables.guiColor)
                    }
                }
            }
        }
    }
}