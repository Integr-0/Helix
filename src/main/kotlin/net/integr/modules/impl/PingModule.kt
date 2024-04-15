package net.integr.modules.impl

import net.integr.Helix
import net.integr.modules.management.UiModule
import net.integr.rendering.uisystem.Box
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.network.PlayerListEntry


class PingModule : UiModule("Ping", "Renders your Ping", "ping", 22,70) {
    private var background: Box = Box(0, 0, 70, 22, "Ping: ", false)

    override fun render(context: DrawContext, originX: Int, originY: Int, delta: Float) {
        background.text = "Ping: ${getPing()}"
        background.update(originX, originY).render(context, 10, 10, delta)
    }

    private fun getPing(): Int {
        if (Helix.MC.networkHandler == null) return 0

        val playerListEntry: PlayerListEntry = Helix.MC.networkHandler!!.getPlayerListEntry(Helix.MC.player!!.uuid) ?: return 0
        return playerListEntry.latency
    }
}