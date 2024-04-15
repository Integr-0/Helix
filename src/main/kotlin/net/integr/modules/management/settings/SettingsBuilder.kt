package net.integr.modules.management.settings

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose

class SettingsBuilder {
    @Expose val options: MutableList<Setting> = mutableListOf()

    fun add(setting: Setting): SettingsBuilder {
        options += setting
        return this
    }

    fun load(obj: JsonObject): SettingsBuilder {
        obj["settings"].asJsonObject["options"].asJsonArray.forEach {
            for (v in options) {
                if (v.id == it.asJsonObject["id"].asString) {
                    v.load(it.asJsonObject)
                }
            }
        }

        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Setting> getById(id: String): T? {
        for (s in options) {
            if (s.id == id) return s as T
        }

        return null
    }
}