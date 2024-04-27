package net.integr.modules.impl

import net.integr.Helix
import net.integr.modules.filters.Filter
import net.integr.modules.management.UiModule
import net.integr.modules.management.settings.impl.BooleanSetting
import net.integr.rendering.uisystem.Box
import net.integr.rendering.uisystem.ToggleButton
import net.minecraft.client.gui.DrawContext

class KeyStrokesModule : UiModule("Keystrokes", "Renders your Key Inputs", "keystrokes", 79, 80, listOf(Filter.Render, Filter.Ui)) {
    init {
        settings.add(BooleanSetting("Spacebar", "Toggle rendering the Spacebar", "spaceBar"))
    }

    private var background: Box = Box(0, 0, 79, 80, null, false)

    private var keyW: ToggleButton = ToggleButton(0, 0, 20, 20, "  W", true, "", false)
    private var keyA: ToggleButton = ToggleButton(0, 0, 20, 20, "  A", true, "", false)
    private var keyS: ToggleButton = ToggleButton(0, 0, 20, 20, "  S", true, "", false)
    private var keyD: ToggleButton = ToggleButton(0, 0, 20, 20, "  D", true, "", false)
    private var keySpace: ToggleButton = ToggleButton(0, 0, 68, 20, "  ━━━━━━━━", true, "", false)

    override fun render(context: DrawContext, originX: Int, originY: Int, delta: Float) {
        keyW.enabled = Helix.MC.options.forwardKey.isPressed
        keyA.enabled = Helix.MC.options.leftKey.isPressed
        keyS.enabled = Helix.MC.options.backKey.isPressed
        keyD.enabled = Helix.MC.options.rightKey.isPressed
        keySpace.enabled = Helix.MC.options.jumpKey.isPressed

        val renderSpace = settings.getById<BooleanSetting>("spaceBar")!!.isEnabled()

        if (!renderSpace) {
            background.ySize = 55
            super.height = 55
        } else {
            background.ySize = 80
            super.height = 80
        }
        background.update(originX, originY).render(context, 0, 0, delta)

        keyW.update(originX + 29, originY + 5).render(context, 0, 0, delta)
        keyA.update(originX + 5, originY + 29).render(context, 0, 0, delta)
        keyS.update(originX + 29, originY + 29).render(context, 0, 0, delta)
        keyD.update(originX + 53, originY + 29).render(context, 0, 0, delta)

        if (renderSpace) keySpace.update(originX + 5, originY + 53).render(context, 0, 0, delta)
    }
}