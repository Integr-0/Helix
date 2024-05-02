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

package net.integr.servercommunicator

import com.google.gson.GsonBuilder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.integr.modules.management.ModuleManager
import net.integr.utilities.LogUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

/**
 * # For the Developers
 * Use this event receiver to disable modules for your server
 *
 * ### Json Structure from the payload
 * ```json
 * {
 *   "disabled": [
 *     "armorHud",
 *     "autoSprint"
 *   ]
 * }
 * ```
 *
 * ### Sending the configuration json in your plugin
 * ```java
 * <player>.sendPluginMessage(<plugin-instance>, "helix:config",
 *      <json-payload>.toByteArray()
 * );
 * ```
 *
 */
class ServerCommunication {
    companion object {
        fun init() {
            ClientPlayNetworking.registerReceiver(Identifier("helix", "config")) { _: MinecraftClient, _: ClientPlayNetworkHandler, buf: PacketByteBuf, _: PacketSender ->
                reset()
                try {
                    val b = buf.readString()
                    val obj = GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create().fromJson(b, JsonObject::class.java)

                    obj["disabled"]!!.jsonArray.forEach {
                        val id = it.jsonPrimitive.content

                        ModuleManager.getById(id)?.lock()
                    }
                } catch (e: Exception) {
                    LogUtils.sendLog("An Error occurred when parsing the configuration payload sent from the server!")
                }
            }
        }

        fun reset() {
            ModuleManager.modules.forEach {
                it.unlock()
            }
        }
    }
}