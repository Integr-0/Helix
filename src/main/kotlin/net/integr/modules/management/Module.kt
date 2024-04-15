package net.integr.modules.management

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import net.integr.Helix
import net.integr.eventsystem.EventSystem
import net.integr.modules.management.settings.SettingsBuilder
import net.integr.utilities.LogUtils
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

open class Module(open var displayName: String, open var toolTip: String, open var id: String) {
    @Expose var enabled = false
    @Expose var settings: SettingsBuilder = SettingsBuilder()

    fun enable() {
        enabled = true
        EventSystem.register(this)
    }

    fun setState(en: Boolean) {
        enabled = en
        if (en) EventSystem.register(this) else EventSystem.unRegister(this)
    }

    fun disable() {
        enabled = false
        EventSystem.unRegister(this)
    }

    fun isEnabled(): Boolean {
        return enabled
    }

    fun save() {
        val json = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().toJson(this)

        try {
            Files.createDirectories(Path.of(Helix.CONFIG.toString()))
            Files.write(Path.of(Helix.CONFIG.toString() + "\\$id.json"), json.toByteArray())
        } catch (e: IOException) {
            LogUtils.sendLog("Could not save config [$id]!")
            e.printStackTrace()
        }
    }

    fun exists(): Boolean {
        return Path.of(Helix.CONFIG.toString() + "\\$id.json").exists()
    }

    open fun load() {
        val json = Files.readAllLines(Path.of(Helix.CONFIG.toString() + "\\$id.json")).joinToString("")
        val jsonObj = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().fromJson(json, JsonObject::class.java)

        this.enabled = jsonObj["enabled"].asBoolean
        this.settings = settings.load(jsonObj)
    }
}