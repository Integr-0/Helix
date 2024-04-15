@file:Suppress("DuplicatedCode")

package net.integr.modules.impl

import net.integr.event.RenderArmEvent
import net.integr.event.RenderHeldItemEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.management.Module
import net.integr.modules.management.settings.impl.BooleanSetting
import net.minecraft.util.Hand
import net.minecraft.util.math.RotationAxis

class BetterArmModule : Module("BetterArm", "Changes rendering of your hand", "betterArm") {
    init {
        settings.add(BooleanSetting("Offhand", "Toggle rendering the offhand differently", "offhand"))
    }

    @EventListen
    fun onRenderItem(event: RenderHeldItemEvent) {
        if (event.hand == Hand.MAIN_HAND) {
            event.matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0f))
            event.matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-3f))
            event.matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0f))
            event.matrices.scale(1f, 1f, 1f)
            event.matrices.translate(0.05, 0.3, -0.3)
        } else if (settings.getById<BooleanSetting>("offhand")!!.isEnabled()){
            event.matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0f))
            event.matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-3f))
            event.matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0f))
            event.matrices.scale(1f, 1f, 1f)
            event.matrices.translate(-0.1, 0.3, -0.3)
        }
    }

    @EventListen
    fun onRenderArm(event: RenderArmEvent) {
        event.matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0f))
        event.matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-3f))
        event.matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0f))
        event.matrices.scale(1f, 1f, 1f)
        event.matrices.translate(0.05, 0.3, -0.3)
    }
}