package net.integr.rendering.screens

import net.integr.Helix
import net.integr.event.UiRenderEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.management.ModuleManager
import net.integr.modules.management.UiModule
import net.integr.rendering.uisystem.Box

class GameScreen {
    private var branding: Box = Box(5, 5, 70, 20, "Helix v${Helix.VERSION}", textCentered = false, outlined = true)

    @EventListen
    fun onRenderUi(event: UiRenderEvent) {
        val context = event.context
        val delta = event.tickDelta

        for (m in ModuleManager.getUiModules()) {
            val module = m as UiModule
            if (m.isEnabled()) module.runRender(context, delta)
        }

        branding.render(context, 0, 0, delta)
    }
}