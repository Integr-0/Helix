package net.integr.rendering.uisystem

import kotlinx.coroutines.Runnable
import net.integr.Helix
import net.integr.Variables
import net.integr.rendering.RenderingEngine
import net.integr.rendering.uisystem.base.HelixUiElement
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Drawable
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents
import org.jetbrains.annotations.Nullable
import org.lwjgl.glfw.GLFW

@Suppress("MemberVisibilityCanBePrivate")
class Button(var xPos: Int, var yPos: Int, var xSize: Int, var ySize: Int, @Nullable var text: String?, var textCentered: Boolean, var tooltip: String, var outlined: Boolean, val action: Runnable) : Drawable, HelixUiElement {
    var textColor: Int = 0

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val colorEnabled = Variables.guiColor
        textColor = Variables.guiBack

        val x1 = xPos
        val x2 = xPos + xSize
        val y1 = yPos
        val y2 = yPos + ySize

        if (outlined) {
            RenderingEngine.TwoDimensional.fillRound(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), textColor, colorEnabled, context, 0.05f, 9f)
        } else {
            RenderingEngine.TwoDimensional.fillRoundNoOutline(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), colorEnabled, context, 0.05f, 9f)
        }

        if (text != null) {
            if (outlined) {
                if (textCentered) {
                    context.drawText(Helix.MC.textRenderer, text, x1 + xSize / 2 - Helix.MC.textRenderer.getWidth(text) / 2 - 4, y1 + ySize / 2 - 4, colorEnabled, false)
                } else {
                    context.drawText(Helix.MC.textRenderer, text, x1 + 2, y1 + ySize / 2 - 4, colorEnabled, false)
                }
            } else {
                if (textCentered) {
                    context.drawText(Helix.MC.textRenderer, text, x1 + xSize / 2 - Helix.MC.textRenderer.getWidth(text) / 2 - 4, y1 + ySize / 2 - 4, textColor, false)
                } else {
                    context.drawText(Helix.MC.textRenderer, text, x1 + 2, y1 + ySize / 2 - 4, textColor, false)
                }
            }
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
                action.run()
                Helix.MC.soundManager.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    override fun onRelease(mouseX: Double, mouseY: Double, button: Int) {
        // Do Nothing
    }

    override fun update(xPos: Int, yPos: Int): Button {
        this.xPos = xPos
        this.yPos = yPos

        return this
    }
}