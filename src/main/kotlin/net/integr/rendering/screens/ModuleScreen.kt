@file:Suppress("DuplicatedCode")

package net.integr.rendering.screens

import net.integr.Helix
import net.integr.modules.management.Module
import net.integr.modules.management.settings.Setting
import net.integr.rendering.uisystem.Box
import net.integr.rendering.uisystem.IconButton
import net.integr.rendering.uisystem.UiLayout
import net.integr.rendering.uisystem.base.HelixUiElement
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class ModuleScreen(mod: Module) : Screen(Text.literal("${mod.displayName} Config")) {
    private val layout = UiLayout()

    private var backgroundBox: Box? = null
    private var titleBox: Box? = null
    private var closeButton: IconButton? = null

    private var preInitSizeY = 5
    private var preInitSizeX = 5 + 200 + 5

    private val settings: MutableList<PosWrapper> = mutableListOf()

    init {

        preInitSizeY += mod.settings.options.count() * 25

        var currY = 5

        for (s in mod.settings.options) {
            val b = layout.add(s.getUiElement()!!)

            s.getUiElement()

            settings += PosWrapper(b, s, currY)

            currY += 20 + 5
        }

        titleBox = layout.add(Box(width/2-preInitSizeX/2, height/2-preInitSizeY/2-45, preInitSizeX, 20, mod.displayName, false, outlined = true)) as Box

        backgroundBox = layout.add(Box(width/2-preInitSizeX/2, height/2-preInitSizeY/2, preInitSizeX, preInitSizeY, null, false)) as Box

        closeButton = layout.add(IconButton(width/2+preInitSizeX/2-15,height/2-preInitSizeY/2-45, 19, 19, "x", "Return to the main gui") {
            Helix.MC.setScreen(MenuScreen.INSTANCE)
        }) as IconButton
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        backgroundBox!!.update(width/2-preInitSizeX/2, height/2-preInitSizeY/2).render(context, mouseX, mouseY, delta)
        titleBox!!.update(width/2-preInitSizeX/2, height/2-preInitSizeY/2-45).render(context, mouseX, mouseY, delta)
        closeButton!!.update(width/2+preInitSizeX/2-15,height/2-preInitSizeY/2-45).render(context, mouseX, mouseY, delta)

        for (s in settings) {
            s.element.update(width/2-preInitSizeX/2+5, height/2-preInitSizeY/2 + s.yO).render(context, mouseX, mouseY, delta)
            if (s.setting != null) s.setting.onUpdate(s.element)
        }

        for (s in settings) s.element.renderTooltip(context, mouseX, mouseY, delta)

        closeButton!!.renderTooltip(context, mouseX, mouseY, delta)
    }

    override fun renderBackground(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        // Do Nothing
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        layout.onClick(mouseX, mouseY, button)
        return true
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        layout.onRelease(mouseX, mouseY, button)
        return true
    }

    override fun close() {
        Helix.MC.setScreen(MenuScreen.INSTANCE)
    }

    data class PosWrapper(val element: HelixUiElement, val setting: Setting?, val yO: Int)
}