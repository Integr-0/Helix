package net.integr.rendering.uisystem

import net.integr.Helix
import net.integr.Variables
import net.integr.rendering.RenderingEngine
import net.integr.rendering.uisystem.base.HelixUiElement
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Drawable
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents
import org.jetbrains.annotations.Nullable
import org.lwjgl.glfw.GLFW

@Suppress("MemberVisibilityCanBePrivate")
class ToggleButton(var xPos: Int, var yPos: Int, var xSize: Int, var ySize: Int, @Nullable var text: String?, var textCentered: Boolean, var tooltip: String, var icon: Boolean = true) : Drawable, HelixUiElement {
    private var color: Int = 0
    private var textColor: Int = 0
    var enabled: Boolean = false

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        textColor = Variables.guiBack

        val colorEnabled = Variables.guiColor
        val colorDisabled = Variables.guiDisabled

        color = if (enabled) {
            colorEnabled
        } else colorDisabled

        val x1 = xPos
        val x2 = xPos + xSize
        val y1 = yPos
        val y2 = yPos + ySize

        RenderingEngine.TwoDimensional.fillRoundNoOutline(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), color, context, 0.05f, 9f)

        if (text != null) {
            if (textCentered) {
                context.drawText(Helix.MC.textRenderer, text, x1 + xSize / 2 - Helix.MC.textRenderer.getWidth(text) / 2 - 4, y1 + ySize / 2 - 4, textColor, false)
            } else {
                context.drawText(Helix.MC.textRenderer, text, x1 + 2, y1 + ySize / 2 - 4, textColor, false)
            }
        }

        if (enabled && icon) {
            context.drawText(Helix.MC.textRenderer, "✔", x1 + xSize-15, y1 + ySize / 2 - 4, textColor, false)
        }
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
                enabled = !enabled
                Helix.MC.soundManager.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F))
            }
        }
    }

    override fun onRelease(mouseX: Double, mouseY: Double, button: Int) {
        // Do nothing
    }

    override fun update(xPos: Int, yPos: Int): HelixUiElement {
        this.xPos = xPos
        this.yPos = yPos

        return this
    }
}