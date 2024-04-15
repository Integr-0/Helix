package net.integr.modules.management.settings.impl

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import net.integr.modules.management.settings.Setting
import net.integr.rendering.uisystem.Slider
import net.integr.rendering.uisystem.base.HelixUiElement
import net.integr.utilities.round

class SliderSetting(private val displayName: String, private val tooltip: String, id: String, private var min: Double, private var max: Double) : Setting(id) {
    @Expose var value = 0.0

    override fun getUiElement(): HelixUiElement {
        val uie = Slider(0, 0, 200, 20, displayName, true, tooltip, min, max)
        uie.fillX = uie.xFillFromValue(value)
        uie.text2 = uie.text + uie.valueFromFillX().round(1)
        return uie
    }

    override fun load(obj: JsonObject): Setting {
        this.value = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().fromJson(obj, SliderSetting::class.java).value
        return this
    }

    override fun onUpdate(el: HelixUiElement) {
        value = (el as Slider).valueFromFillX()
    }

    fun getSetValue(): Double {
        return value.round(1)
    }
}