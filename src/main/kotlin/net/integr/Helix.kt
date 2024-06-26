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

package net.integr

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.loader.api.FabricLoader
import net.integr.commands.CommandRegistry
import net.integr.discord.PresenceHandler
import net.integr.event.ClientEndEvent
import net.integr.event.JoinWorldEvent
import net.integr.event.RenderTitleScreenEvent
import net.integr.event.UnsafePreTickEvent
import net.integr.eventsystem.EventListen
import net.integr.eventsystem.EventSystem
import net.integr.eventsystem.Priority
import net.integr.modules.management.ModuleManager
import net.integr.modules.management.settings.impl.CyclerSetting
import net.integr.modules.management.settings.impl.SliderSetting
import net.integr.rendering.RenderingEngine
import net.integr.rendering.screens.GameScreen
import net.integr.rendering.screens.MenuScreen
import net.integr.rendering.uisystem.Box
import net.integr.servercommunicator.ServerCommunication
import net.integr.utilities.LogUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.util.Formatting
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.nio.file.Path


class Helix : ClientModInitializer, ModInitializer {

    companion object {
        val MC: MinecraftClient = MinecraftClient.getInstance()
        val LOGGER: Logger = LoggerFactory.getLogger("Helix")
        val VERSION: String = FabricLoader.getInstance().getModContainer("helix").get().metadata.version.toString()
        val CONFIG: Path = Path.of(FabricLoader.getInstance().configDir.toString().substringBeforeLast("config") + "helix")
        const val DISCORD_ID = "1229042491815104594"

        var openKey: KeyBinding? = null

    }

    private var startTime = 0L

    private var branding: Box = Box(4, 49, 110, 40, null, textCentered = false, outlined = true)

    private val savingThread = Thread {
        while (true) {
            Thread.sleep(50000)
            ModuleManager.save()
            LogUtils.sendLog("Saved your settings [Periodic]!")
        }
    }

    override fun onInitialize() {
        openKey = KeyBindingHelper.registerKeyBinding(KeyBinding("key.helix.openmenu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.helix"))

        startTime = System.currentTimeMillis()
        LogUtils.sendLog("Helix startup...")

        ModuleManager.init()

        EventSystem.register(this)
        EventSystem.register(GameScreen())

        LogUtils.sendLog("Helix started [${System.currentTimeMillis() - startTime}ms]")

        PresenceHandler.start()
        CommandRegistry.init()

        savingThread.name = "Helix Save-Thread"
        savingThread.start()
    }

    @EventListen(Priority.HIGH)
    fun onStop(event: ClientEndEvent) {
        ModuleManager.save()
        PresenceHandler.stop()
        LogUtils.sendLog("Saved your settings [Game Stopped]!")
    }

    @EventListen(Priority.HIGH)
    fun onTickPre(event: UnsafePreTickEvent) {
        if (MC.window != null) MC.window.setTitle("Helix [$VERSION]")

        if (openKey!!.wasPressed()) {
            MC.setScreen(MenuScreen.INSTANCE)
        }

        when (Settings.INSTANCE.settings.getById<CyclerSetting>("theme")!!.getElement()) {
            "Dark" -> Variables.guiBack = Color(25, 24, 24).rgb
            "Light" -> Variables.guiBack = Color(217, 217, 217).rgb
        }

        when (Settings.INSTANCE.settings.getById<CyclerSetting>("accent")!!.getElement()) {
            "Rgb" -> updateAccent(rgb = true)
            "Orange" -> updateAccent(value = Color(249, 141, 0).rgb)
            "Red" -> updateAccent(value = Color(194, 0, 6).rgb)
            "Green" -> updateAccent(value = Color(0, 175, 10).rgb)
            "Yellow" -> updateAccent(value = Color(187, 170, 0).rgb)
            "Pink" -> updateAccent(value = Color(213, 141, 203).rgb)
            "Purple" -> updateAccent(value = Color(129, 0, 249).rgb)
            "Blue" -> updateAccent(value = Color(0, 87, 249).rgb)
        }

        if (Variables.guiColorIsRgb) {
            val rgbColor = RenderingEngine.Misc.getRainbowColor()
            Variables.guiColor = Color(rgbColor[0], rgbColor[1], rgbColor[2]).rgb
        }

        PresenceHandler.setDynamically()
    }

    private fun updateAccent(value: Int = 0, rgb: Boolean = false) {
        Variables.guiColor = value
        Variables.guiColorIsRgb = rgb
    }

    @EventListen
    fun onRenderTitleScreen(event: RenderTitleScreenEvent) {
        branding.render(event.context, event.mouseX, event.mouseY, event.delta)

        RenderingEngine.Text.drawScaled(event.context, Formatting.BOLD.toString() + "Helix", 8, 55, Variables.guiColor)
        RenderingEngine.Text.drawScaled(event.context, "by Integr", 8, 65, Variables.guiColor)
        RenderingEngine.Text.drawScaled(event.context, "v$VERSION", 8, 75, Variables.guiColor)
    }

    @EventListen
    fun onJoinWorld(event: JoinWorldEvent) {
        ServerCommunication.reset()
        ServerCommunication.init()
    }

    override fun onInitializeClient() {

    }
}