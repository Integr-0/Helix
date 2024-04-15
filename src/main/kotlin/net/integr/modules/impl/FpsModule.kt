package net.integr.modules.impl

import net.integr.Helix
import net.integr.modules.management.UiModule
import net.integr.rendering.uisystem.Box
import net.minecraft.client.gui.DrawContext

class FpsModule : UiModule("Fps", "Renders your Fps", "fps", 22,60) {
    private var background: Box = Box(0, 0, 60, 22, "Fps: ", false)

    override fun render(context: DrawContext, originX: Int, originY: Int, delta: Float) {
        background.text = "Fps: ${Helix.MC.currentFps}"
        background.update(originX, originY).render(context, 10, 10, delta)
    }
}