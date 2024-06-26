/*
 * Copyright © 2024 Integr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("DuplicatedCode")

package net.integr.rendering.uisystem

import net.integr.Helix
import net.integr.Settings
import net.integr.Variables
import net.integr.modules.management.settings.impl.SliderSetting
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

        RenderingEngine.TwoDimensional.fillRoundNoOutlineScaled(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), color, context, 0.05f, 9f)

        if (textCentered) {
            RenderingEngine.Text.drawScaled(context, text2, x1 + xSize / 2 - Helix.MC.textRenderer.getWidth(text2) / 2 - 4, y1 + ySize / 2 - 4, textColor)
        } else {
            RenderingEngine.Text.drawScaled(context, text2, x1 + 2, y1 + ySize / 2 - 4, textColor)
        }

        RenderingEngine.Text.drawScaled(context, "→", x1 + xSize-15, y1 + ySize / 2 - 4, textColor)
    }

    override fun renderTooltip(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val x1 = RenderingEngine.Misc.scale(xPos)
        val x2 = RenderingEngine.Misc.scale(xPos + xSize)
        val y1 = RenderingEngine.Misc.scale(yPos)
        val y2 = RenderingEngine.Misc.scale(yPos + ySize)


        if (RenderingEngine.Misc.scale(mouseX) in (x1 + 1)..<x2 && RenderingEngine.Misc.scale(mouseY) > y1 && RenderingEngine.Misc.scale(mouseY) < y2) {
            val explainXSize: Int = Helix.MC.textRenderer.getWidth(tooltip) + 30
            val explainingBox = Box(xPos, yPos, explainXSize, ySize, tooltip, true)

            explainingBox.xPos = mouseX + 10
            explainingBox.yPos = mouseY
            explainingBox.render(context, mouseX, mouseY, delta)
        }
    }

    override fun onClick(mouseX: Double, mouseY: Double, button: Int) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            val x1 = RenderingEngine.Misc.scale(xPos)
            val x2 = RenderingEngine.Misc.scale(xPos + xSize)
            val y1 = RenderingEngine.Misc.scale(yPos)
            val y2 = RenderingEngine.Misc.scale(yPos + ySize)


            if (RenderingEngine.Misc.scale(mouseX) in (x1 + 1)..<x2 && RenderingEngine.Misc.scale(mouseY) > y1 && RenderingEngine.Misc.scale(mouseY) < y2) {
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