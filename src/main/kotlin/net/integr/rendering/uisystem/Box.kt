package net.integr.rendering.uisystem

import net.integr.Helix
import net.integr.Variables
import net.integr.rendering.RenderingEngine
import net.integr.rendering.uisystem.base.HelixUiElement
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Drawable
import org.jetbrains.annotations.Nullable

@Suppress("MemberVisibilityCanBePrivate")
class Box(var xPos: Int, var yPos: Int, var xSize: Int, var ySize: Int, @Nullable var text: String?, var textCentered: Boolean, var outlined: Boolean = true) : Drawable, HelixUiElement {
    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val color = Variables.guiBack
        val textColor = Variables.guiColor
        val x1 = xPos
        val x2 = xPos + xSize
        val y1 = yPos
        val y2 = yPos + ySize

        if (outlined) {
            RenderingEngine.TwoDimensional.fillRound(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), color, textColor, context, 0.05f, 9f)
        } else {
            RenderingEngine.TwoDimensional.fillRoundNoOutline(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), color, context, 0.05f, 9f)
        }

        if (text != null) {
            if (textCentered) {
                context.drawText(Helix.MC.textRenderer, text, x1 + xSize / 2 - Helix.MC.textRenderer.getWidth(text) / 2 - 4, y1 + ySize / 2 - 4, textColor, false)
            } else {
                if (outlined) {
                    context.drawText(Helix.MC.textRenderer, text, x1 + 7, y1 + ySize / 2 - 4, textColor, false)
                } else {
                    context.drawText(Helix.MC.textRenderer, text, x1 + 2, y1 + ySize / 2 - 4, textColor, false)
                }
            }
        }
    }

    override fun renderTooltip(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        // Do Nothing
    }

    override fun onClick(mouseX: Double, mouseY: Double, button: Int) {
        // Do Nothing
    }

    override fun onRelease(mouseX: Double, mouseY: Double, button: Int) {
        // Do Nothing
    }

    override fun update(xPos: Int, yPos: Int): Box {
        this.xPos = xPos
        this.yPos = yPos

        return this
    }
}