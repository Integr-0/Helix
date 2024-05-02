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

@file:Suppress("DuplicatedCode")

package net.integr.modules.management

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import net.integr.Helix
import net.integr.eventsystem.EventSystem
import net.integr.modules.filters.Filter
import net.integr.modules.management.settings.SettingsBuilder
import net.integr.utilities.LogUtils
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

open class Module(open var displayName: String, open var toolTip: String, open var id: String, open var filters: List<Filter> = listOf()) {
    @Expose var enabled = false
    @Expose var settings: SettingsBuilder = SettingsBuilder()

    private var locked = false

    fun enable() {
        if (locked) return
        enabled = true
        EventSystem.register(this)
    }

    fun setState(en: Boolean) {
        if (locked) return
        enabled = en
        if (en) EventSystem.register(this) else EventSystem.unRegister(this)
    }

    fun disable() {
        enabled = false
        EventSystem.unRegister(this)
    }

    fun lock() {
        disable()
        locked = true
    }

    fun unlock() {
        locked = false
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

    fun getSearchingTags(): List<String> {
        val fList: MutableList<String> = mutableListOf()
        for (filter in filters) {fList += filter.name.lowercase().filter { it != ' ' }}

        return listOf(displayName.lowercase().filter { it != ' ' }, toolTip.lowercase().filter { it != ' ' }, id.lowercase().filter { it != ' ' }, fList.joinToString(""))
    }
}