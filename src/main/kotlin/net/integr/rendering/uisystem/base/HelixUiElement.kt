package net.integr.rendering.uisystem.base

import net.minecraft.client.gui.DrawContext

interface HelixUiElement {
    fun onClick(mouseX: Double, mouseY: Double, button: Int)
    fun onRelease(mouseX: Double, mouseY: Double, button: Int)
    fun update(xPos: Int, yPos: Int): HelixUiElement
    fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float)
    fun renderTooltip(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float)
}