package net.integr.rendering.screens

import net.integr.Helix
import net.integr.modules.management.ModuleManager
import net.integr.modules.management.UiModule
import net.integr.rendering.uisystem.MovableBox
import net.integr.rendering.uisystem.UiLayout
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class UiMoveScreen : Screen(Text.literal("Helix Ui-Move Screen")){
    companion object {
        val INSTANCE: Screen = UiMoveScreen()
    }

    private val layout = UiLayout()

    private val boxes: MutableList<BoxWrapper> = mutableListOf()

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        for (w in boxes) {
            val box = w.box
            val module = w.module

            box.ySize = module.height
            box.xSize = module.width
            box.render(context, mouseX, mouseY, delta)
            module.updatePosition(box.xPos, box.yPos)
        }
    }

    init {
        for (m in ModuleManager.getUiModules()) {
            val module = m as UiModule

            val box = layout.add(module.getPlaceholderBox()) as MovableBox

            boxes.add(BoxWrapper(box, module))
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

    override fun close() {
        Helix.MC.setScreen(MenuScreen.INSTANCE)
    }

    data class BoxWrapper(val box: MovableBox, val module: UiModule)

}