@file:Suppress("DuplicatedCode")

package net.integr.modules.management

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import net.integr.Helix
import net.integr.rendering.uisystem.MovableBox
import net.minecraft.client.gui.DrawContext
import java.nio.file.Files
import java.nio.file.Path

open class UiModule(override var displayName: String, override var toolTip: String, override var id: String, private var height: Int, private var width: Int) : Module(displayName, toolTip, id) {
    @Expose var originX: Int = 20
    @Expose var originY: Int = 20

    fun runRender(context: DrawContext, delta: Float) {
        render(context, originX, originY, delta)
    }

    fun updatePosition(x: Int, y: Int) {
        originX = x
        originY = y
    }

    open fun render(context: DrawContext, originX: Int, originY: Int, delta: Float) {

    }

    override fun load() {
        val json = Files.readAllLines(Path.of(Helix.CONFIG.toString() + "\\$id.json")).joinToString("")
        val jsonObj = GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create().fromJson(json, JsonObject::class.java)

        this.enabled = jsonObj["enabled"].asBoolean
        this.originX = jsonObj["originX"].asInt
        this.originY = jsonObj["originY"].asInt

        this.settings = settings.load(jsonObj)
    }

    fun getPlaceholderBox(): MovableBox {
        return MovableBox(originX, originY, width, height, displayName)
    }
}