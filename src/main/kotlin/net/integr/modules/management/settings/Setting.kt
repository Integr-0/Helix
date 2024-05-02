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

package net.integr.modules.management.settings

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import net.integr.rendering.uisystem.base.HelixUiElement

open class Setting(@Expose val id: String) {
    open fun getUiElement(): HelixUiElement? {
        return null
    }

    open fun load(obj: JsonObject): Setting? {
        return null
    }

    open fun onUpdate(el: HelixUiElement) {

    }
}

