package net.integr

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import net.integr.modules.management.settings.Setting
import net.integr.modules.management.settings.SettingsBuilder
import net.integr.modules.management.settings.impl.BooleanSetting
import net.integr.modules.management.settings.impl.CyclerSetting
import net.integr.utilities.LogUtils
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

class Settings {
    companion object {
        val INSTANCE = Settings()
    }

    @Expose var settings: SettingsBuilder = SettingsBuilder()
        .add(CyclerSetting("Accent: ", "The accent for the Client", "accent", mutableListOf("Rgb", "Orange", "Red", "Green", "Yellow", "Pink", "Purple", "Blue")))
        .add(CyclerSetting("Theme: ", "The theme for the Client", "theme", mutableListOf("Dark", "Light")))
        .add(BooleanSetting("DiscordRPC", "Toggle the discord Presence", "discordRpc"))

    fun save() {
        val json = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(this)

        try {
            Files.createDirectories(Path.of(Helix.CONFIG.toString() + "\\settings"))
            Files.write(Path.of(Helix.CONFIG.toString() + "\\settings\\settings.json"), json.toByteArray())
        } catch (e: IOException) {
            LogUtils.sendLog("Could not save config [Settings]!")
            e.printStackTrace()
        }
    }

    fun exist(): Boolean {
        return Path.of(Helix.CONFIG.toString() + "\\settings\\settings.json").exists()
    }

    fun load() {
        val json = Files.readAllLines(Path.of(Helix.CONFIG.toString() + "\\settings\\settings.json")).joinToString("")
        val jsonObj = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().fromJson(json, JsonObject::class.java)

        this.settings = settings.load(jsonObj)
    }
}