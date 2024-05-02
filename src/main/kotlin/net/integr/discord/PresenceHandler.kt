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

package net.integr.discord

import club.minnced.discord.rpc.DiscordEventHandlers
import club.minnced.discord.rpc.DiscordRPC
import club.minnced.discord.rpc.DiscordRichPresence
import net.integr.Helix
import net.integr.Settings
import net.integr.modules.management.settings.impl.BooleanSetting

class PresenceHandler {
    companion object {
        private var start: Long = 0

        private fun setServer(server: String, maxPlayers: Int, players: Int) {
            val pres = DiscordRichPresence()

            pres.largeImageKey = "helix_square"
            pres.smallImageKey = "integr"
            pres.details = "Playing $server"
            pres.state = "Online"
            pres.partyMax = maxPlayers
            pres.partySize = players
            pres.startTimestamp = start
            pres.smallImageText = "by Integr"
            pres.largeImageText = "Helix [" + Helix.VERSION + "]"

            DiscordRPC.INSTANCE.Discord_UpdatePresence(pres)
        }

        private fun setMenu() {
            val pres = DiscordRichPresence()

            pres.largeImageKey = "helix_square"
            pres.smallImageKey = "integr"
            pres.details = "In Menu"
            pres.startTimestamp = start
            pres.smallImageText = "by Integr"
            pres.largeImageText = "Helix [" + Helix.VERSION + "]"

            DiscordRPC.INSTANCE.Discord_UpdatePresence(pres)
        }

        private fun setSinglePlayer() {
            val pres = DiscordRichPresence()

            pres.largeImageKey = "helix_square"
            pres.smallImageKey = "integr"
            pres.details = "Playing SinglePlayer"
            pres.startTimestamp = start
            pres.smallImageText = "by Integr"
            pres.largeImageText = "Helix [" + Helix.VERSION + "]"

            DiscordRPC.INSTANCE.Discord_UpdatePresence(pres)
        }

        private fun init() {
            val handlers = DiscordEventHandlers()

            DiscordRPC.INSTANCE.Discord_Initialize(Helix.DISCORD_ID, handlers, true, "")
        }

        fun stop() {
            clear()
            DiscordRPC.INSTANCE.Discord_Shutdown()
        }

        fun start() {
            start = System.currentTimeMillis()
            init()
        }

        private fun clear() {
            DiscordRPC.INSTANCE.Discord_ClearPresence()
        }

        fun setDynamically() {
            if (Settings.INSTANCE.settings.getById<BooleanSetting>("discordRpc")!!.isEnabled()) {
                if (Helix.MC.player != null && Helix.MC.world != null) {
                    if (!Helix.MC.isIntegratedServerRunning && Helix.MC.currentServerEntry != null && Helix.MC.currentServerEntry!!.players != null) {
                        setServer(Helix.MC.currentServerEntry!!.address, Helix.MC.currentServerEntry!!.players!!.max(), Helix.MC.currentServerEntry!!.players!!.online())
                    } else if (Helix.MC.isIntegratedServerRunning) {
                        setSinglePlayer()
                    }
                } else {
                    setMenu()
                }
            } else clear()
        }
    }
}