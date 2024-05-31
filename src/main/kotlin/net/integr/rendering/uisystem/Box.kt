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

package net.integr.rendering.uisystem

import net.integr.Helix
import net.integr.Settings
import net.integr.Variables
import net.integr.modules.management.settings.impl.SliderSetting
import net.integr.rendering.RenderingEngine
import net.integr.rendering.uisystem.base.HelixUiElement
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.Drawable
import org.jetbrains.annotations.Nullable

@Suppress("MemberVisibilityCanBePrivate")
class Box(var xPos: Int, var yPos: Int, var xSize: Int, var ySize: Int, @Nullable var text: String?, var textCentered: Boolean, var outlined: Boolean = true, var innerIsDisabled: Boolean = false) : Drawable, HelixUiElement {
    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        val color = Variables.guiBack
        val textColor = Variables.guiColor
        val x1 = xPos
        val x2 = xPos + xSize
        val y1 = yPos
        val y2 = yPos + ySize

        if (outlined) {
            if (innerIsDisabled) {
                RenderingEngine.TwoDimensional.fillRoundScaled(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), Variables.guiDisabled, textColor, context, 0.05f, 9f)
            } else RenderingEngine.TwoDimensional.fillRoundScaled(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), color, textColor, context, 0.05f, 9f)
        } else {
            RenderingEngine.TwoDimensional.fillRoundNoOutlineScaled(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), color, context, 0.05f, 9f)
        }

        if (text != null) {
            if (textCentered) {
                RenderingEngine.Text.drawScaled(context, text!!, x1+xSize/2-Helix.MC.textRenderer.getWidth(text)/2-4, y1+ySize/2-4, textColor)
            } else {
                if (outlined) {
                    RenderingEngine.Text.drawScaled(context, text!!, x1 + 7, y1+ySize/2-4, textColor)
                } else {
                    RenderingEngine.Text.drawScaled(context, text!!, x1 + 2, y1+ySize/2-4, textColor)
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