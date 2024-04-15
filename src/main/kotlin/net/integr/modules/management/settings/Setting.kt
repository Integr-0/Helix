package net.integr.modules.management.settings

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import net.integr.rendering.uisystem.base.HelixUiElement
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

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

