package net.integr.modules.impl

import net.integr.Helix
import net.integr.modules.filters.Filter
import net.integr.modules.management.UiModule
import net.integr.rendering.uisystem.Box
import net.minecraft.client.gui.DrawContext

class DirectionsModule : UiModule("Direction", "Renders your Direction", "direction", 22,110, listOf(Filter.Render, Filter.Ui)) {
    private var background: Box = Box(0, 0, 110, 22, "Direction: ", false)

    override fun render(context: DrawContext, originX: Int, originY: Int, delta: Float) {
        background.text = "Direction: ${Helix.MC.player!!.movementDirection}"
        background.update(originX, originY).render(context, 10, 10, delta)
    }
}