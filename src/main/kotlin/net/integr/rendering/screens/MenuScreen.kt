package net.integr.rendering.screens

import net.integr.Helix
import net.integr.modules.management.ModuleManager
import net.integr.rendering.uisystem.*
import net.integr.rendering.uisystem.base.HelixUiElement
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class MenuScreen : Screen(Text.literal("Helix Menu")) {
    companion object {
        val INSTANCE: Screen = MenuScreen()
    }

    private val layout = UiLayout()

    private var preInitSizeY = 10 + 20
    private var preInitSizeX = 5 + 200 + 5 + 200 + 5 + 200 + 5

    private var backgroundBox: Box? = null
    private var actionRowBox: Box? = null
    private var moveButton: IconButton? = null
    private var settingsButton: IconButton? = null

    private val modules: MutableList<PosWrapper> = mutableListOf()

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        backgroundBox!!.update(width/2-preInitSizeX/2, height/2-preInitSizeY/2).render(context, mouseX, mouseY, delta)
        actionRowBox!!.update(width/2-preInitSizeX/2, height/2-preInitSizeY/2-45).render(context, mouseX, mouseY, delta)
        moveButton!!.update(width/2+preInitSizeX/2-19,height/2-preInitSizeY/2-45).render(context, mouseX, mouseY, delta)
        settingsButton!!.update(width/2+preInitSizeX/2-43,height/2-preInitSizeY/2-45).render(context, mouseX, mouseY, delta)


        for (l in modules) {
            l.element.update(width/2-preInitSizeX/2 + l.xO, height/2-preInitSizeY/2 + l.yO).render(context, mouseX, mouseY, delta)
        }

        for (l in modules) l.element.renderTooltip(context, mouseX, mouseY, delta)

        moveButton!!.renderTooltip(context, mouseX, mouseY, delta)
        settingsButton!!.renderTooltip(context, mouseX, mouseY, delta)
    }

    private fun getXFromIndex(index: Int): Int {
        return when (index) {
            0 -> 5
            1 -> 5 + 200 + 5
            2 -> 5 + 200 + 5 + 200 + 5
            else -> 1
        }
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

    init {
        preInitSizeY += (ModuleManager.modules.count() / 3) * 25

        var currY = 5
        var mCount = 0;

        for (m in ModuleManager.modules) {
            val b = layout.add(ModuleButton(width/2-preInitSizeX/2 + getXFromIndex(ModuleManager.modules.indexOf(m)%3), height/2-preInitSizeY/2 + currY, 200, 20, m.displayName, true, m.toolTip, false, m))

            modules += PosWrapper(b, getXFromIndex(ModuleManager.modules.indexOf(m)%3), currY)

            if (mCount == 2) {
                currY += 20 + 5
                mCount = 0
            } else mCount++
        }

        backgroundBox = layout.add(Box(width/2-preInitSizeX/2, height/2-preInitSizeY/2, preInitSizeX, preInitSizeY, null, false)) as Box
        actionRowBox = layout.add(Box(width/2-preInitSizeX/2, height/2-preInitSizeY/2-45, preInitSizeX, 20, "Helix", false)) as Box

        moveButton = layout.add(IconButton(width/2+preInitSizeX/2-19,height/2-preInitSizeY/2-45, 20, 20, "⌂", "Move the UI elements") {
            Helix.MC.setScreen(UiMoveScreen.INSTANCE)
        }) as IconButton

        settingsButton = layout.add(IconButton(width/2+preInitSizeX/2-43,height/2-preInitSizeY/2-45, 20, 20, "≡", "Open the general settings") {
            Helix.MC.setScreen(SettingsScreen())
        }) as IconButton

    }

    data class PosWrapper(val element: HelixUiElement, val xO: Int, val yO: Int)
}