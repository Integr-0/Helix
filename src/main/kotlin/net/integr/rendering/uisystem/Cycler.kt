@file:Suppress("DuplicatedCode")

package net.integr.rendering.uisystem

import net.integr.Helix
import net.integr.Variables
import net.integr.rendering.RenderingEngine
import net.integr.rendering.uisystem.base.HelixUiElement
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Drawable
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents
import org.lwjgl.glfw.GLFW

@Suppress("MemberVisibilityCanBePrivate")
class Cycler(var xPos: Int, var yPos: Int, var xSize: Int, var ySize: Int, var text: String, var textCentered: Boolean, var tooltip: String, val items: MutableList<String>) : Drawable, HelixUiElement {
    var currentIndex = 0
    var text2 = text

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val color = Variables.guiColor
        val textColor = Variables.guiBack
        val x1 = xPos
        val x2 = xPos + xSize
        val y1 = yPos
        val y2 = yPos + ySize

        RenderingEngine.TwoDimensional.fillRoundNoOutline(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), color, context, 0.05f, 9f)

        if (textCentered) {
            context.drawText(Helix.MC.textRenderer, text2, x1 + xSize / 2 - Helix.MC.textRenderer.getWidth(text2) / 2 - 4, y1 + ySize / 2 - 4, textColor, false)
        } else {
            context.drawText(Helix.MC.textRenderer, text2, x1 + 2, y1 + ySize / 2 - 4, textColor, false)
        }

        context.drawText(Helix.MC.textRenderer, "→", x1 + xSize-15, y1 + ySize / 2 - 4, textColor, false)
    }

    override fun renderTooltip(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val x1 = xPos
        val x2 = xPos + xSize
        val y1 = yPos
        val y2 = yPos + ySize

        if (mouseX in (x1 + 1)..<x2 && mouseY > y1 && mouseY < y2) {
            val explainXSize: Int = Helix.MC.textRenderer.getWidth(tooltip) + 30
            val explainingBox = Box(xPos, yPos, explainXSize, ySize, tooltip, true)

            explainingBox.xPos = mouseX + 10
            explainingBox.yPos = mouseY
            explainingBox.render(context, mouseX, mouseY, delta)
        }
    }

    override fun onClick(mouseX: Double, mouseY: Double, button: Int) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            val x1 = xPos
            val x2 = xPos + xSize
            val y1 = yPos
            val y2 = yPos + ySize

            if (mouseX.toInt() in (x1 + 1)..<x2 && mouseY > y1 && mouseY < y2) {
                if (currentIndex == items.count()-1) currentIndex = 0 else currentIndex++
                text2 = text + items[currentIndex]
                Helix.MC.soundManager.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F))
            }
        }
    }

    override fun onRelease(mouseX: Double, mouseY: Double, button: Int) {
        // Do Nothing
    }

    override fun update(xPos: Int, yPos: Int): Cycler {
        this.xPos = xPos
        this.yPos = yPos

        return this
    }
}