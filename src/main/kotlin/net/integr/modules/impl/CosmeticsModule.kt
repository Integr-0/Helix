package net.integr.modules.impl

import net.integr.Helix
import net.integr.Variables
import net.integr.event.RenderWorldEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.management.Module
import net.integr.modules.management.settings.impl.BooleanSetting
import net.integr.rendering.RenderingEngine
import net.integr.utilities.CoordinateUtils
import net.minecraft.util.math.Vec3d

class CosmeticsModule : Module("Cosmetics", "Gives you cosmetics", "cosmetics") {
    init {
        settings.add(BooleanSetting("China Hat", "Render a China hat", "chinaHat"))
    }

    @EventListen
    fun onRender(event: RenderWorldEvent) {
        if (settings.getById<BooleanSetting>("chinaHat")!!.isEnabled()) {
            val pos: Vec3d = CoordinateUtils.getLerpedEntityPos(Helix.MC.player!!, event.tickDelta)
            RenderingEngine.ThreeDimensional.circle(pos.add(0.0, Helix.MC.player!!.boundingBox.lengthY+0.2, 0.0), 0.1, 1.0, 0.2f, true, event.matrices, Variables.guiColor)
        }
    }
}