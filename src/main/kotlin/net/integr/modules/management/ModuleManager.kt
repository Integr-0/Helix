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

@file:Suppress("unused")

package net.integr.modules.management

import net.integr.Helix
import net.integr.Settings
import net.integr.eventsystem.EventSystem
import net.integr.utilities.LogUtils
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.reflect.KClass

class ModuleManager {
    companion object {
        val modules: MutableList<Module> = mutableListOf()
        private var uiModules: MutableList<Module> = mutableListOf()

        fun init() {
            val loaded = ModuleLoader.load()

            loaded.forEach {
                val v = it.getConstructor().newInstance()
                modules += v as Module
            }

            load()

            uiModules = modules.filter { UiModule::class.java.isAssignableFrom(it::class.java) }.toMutableList()

            LogUtils.sendLog("Loaded ${loaded.count()- uiModules.count()} module/s")
            LogUtils.sendLog("Loaded ${uiModules.count()} UI module/s")
        }

        fun getById(id: String): Module? {
            for (m in modules) {
                if (m.id == id) {
                    return m
                }
            }

            return null
        }

        fun getByClass(klass: KClass<*>): Module? {
            for (m in modules) {
                if (m.javaClass == klass.java) {
                    return m
                }
            }

            return null
        }

        fun getByClass(klass: Class<*>): Module? {
            for (m in modules) {
                if (m.javaClass == klass) {
                    return m
                }
            }

            return null
        }

        fun getUiModules(): List<Module> {
            return uiModules
        }

        fun save() {
            for (m in modules) {
                m.save()
            }

            Settings.INSTANCE.save()
        }

        private fun load() {
            if (!Path.of(Helix.CONFIG.toString()).exists()) {
                save()
            }

            if (!Path.of(Helix.CONFIG.toString() + "\\settings").exists()) {
                save()
            }

            if (!Settings.INSTANCE.exist()) {
                Settings.INSTANCE.save()
            }

            try {
                Settings.INSTANCE.load()
            } catch (e: Exception) {
                LogUtils.sendLog("Something went wrong while loading Settings, resetting to default!")
                Settings.INSTANCE.save()
            }

            for (m in modules) {
                if (!m.exists()) {
                    m.save()
                }

                try {
                    m.load()
                } catch (e: Exception) {
                    LogUtils.sendLog("Something went wrong while loading ${m.id}, resetting the module!")
                    m.save()
                }

                if (m.enabled) {
                    EventSystem.register(m)
                }
            }
        }
    }
}