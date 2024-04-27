package net.integr.modules.impl

import net.integr.Helix
import net.integr.modules.filters.Filter
import net.integr.modules.management.UiModule
import net.integr.modules.management.settings.impl.BooleanSetting
import net.integr.rendering.uisystem.Box
import net.minecraft.client.gui.DrawContext

class CoordinatesModule : UiModule("Coordinates", "Renders your Coordinates", "coordinates", 60,100, listOf(Filter.Render, Filter.Ui)) {
    init {
        settings.add(BooleanSetting("Swap X and Y", "Swap Coordinates to protect yourself", "swapXY"))
        settings.add(BooleanSetting("Swap Y and Z", "Swap Coordinates to protect yourself", "swapYZ"))
        settings.add(BooleanSetting("Swap Z and X", "Swap Coordinates to protect yourself", "swapZX"))
    }

    private var background: Box = Box(0, 0, 100, 60, null, false)

    private var coordinatesXBox: Box = Box(0, 0, 100, 20, null, false, outlined = false)
    private var coordinatesYBox: Box = Box(0, 0, 100, 20, null, false, outlined = false)
    private var coordinatesZBox: Box = Box(0, 0, 100, 20, null, false, outlined = false)

    override fun render(context: DrawContext, originX: Int, originY: Int, delta: Float) {
        var x = Helix.MC.player!!.blockX
        var y = Helix.MC.player!!.blockY
        var z = Helix.MC.player!!.blockZ

        if (settings.getById<BooleanSetting>("swapXY")!!.isEnabled()) x = y.also { y = x }
        if (settings.getById<BooleanSetting>("swapYZ")!!.isEnabled()) y = z.also { z = y }
        if (settings.getById<BooleanSetting>("swapZX")!!.isEnabled()) z = x.also { x = z }

        coordinatesXBox.text = " X: $x"
        coordinatesYBox.text = " Y: $y"
        coordinatesZBox.text = " Z: $z"

        background.update(originX, originY).render(context, 10, 10, delta)
        coordinatesXBox.update(originX, originY).render(context, 10, 10, delta)
        coordinatesYBox.update(originX, originY+20).render(context, 10, 10, delta)
        coordinatesZBox.update(originX, originY+40).render(context, 10, 10, delta)
    }
}