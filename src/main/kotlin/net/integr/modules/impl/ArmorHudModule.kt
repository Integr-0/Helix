@file:Suppress("DuplicatedCode")

package net.integr.modules.impl

import net.integr.Helix
import net.integr.Variables
import net.integr.modules.management.UiModule
import net.integr.modules.management.settings.impl.BooleanSetting
import net.integr.rendering.uisystem.Box
import net.minecraft.client.gui.DrawContext

class ArmorHudModule : UiModule("ArmorHud", "Render your armor durability on screen", "armorHud", 85, 60) {
    init {
        settings.add(BooleanSetting("Use Percentage", "Changes the rendering to be % instead of the default rendering", "percent"))
    }

    private val background = Box(0, 0, 60, 85, null, false, outlined = true)

    override fun render(context: DrawContext, originX: Int, originY: Int, delta: Float) {
        val helmetItem = Helix.MC.player!!.inventory.armor[3]
        val chestPlateItem = Helix.MC.player!!.inventory.armor[2]
        val leggingsItem = Helix.MC.player!!.inventory.armor[1]
        val bootsItem = Helix.MC.player!!.inventory.armor[0]

        if (settings.getById<BooleanSetting>("percent")!!.isEnabled()) {
            background.xSize = 60
            super.width = 60
            background.update(originX, originY).render(context, 0, 0, delta)

            context.drawItem(helmetItem, originX + 5, originY+5)
            context.drawItem(chestPlateItem, originX + 5, originY + 25)
            context.drawItem(leggingsItem, originX + 5, originY + 45)
            context.drawItem(bootsItem, originX + 5, originY + 65)

            val helmetPercent = if (helmetItem.maxDamage != 0) (helmetItem.maxDamage.toDouble() - helmetItem.damage.toDouble())  / (helmetItem.maxDamage.toDouble()) * 100.0 else 0
            val chestPlatePercent = if (chestPlateItem.maxDamage != 0) (chestPlateItem.maxDamage.toDouble() - chestPlateItem.damage.toDouble()) / (chestPlateItem.maxDamage.toDouble()) * 100.0 else 0
            val leggingsPercent = if (leggingsItem.maxDamage != 0) (leggingsItem.maxDamage.toDouble() - leggingsItem.damage.toDouble()) / (leggingsItem.maxDamage.toDouble()) * 100.0 else 0
            val bootsPercent = if (bootsItem.maxDamage != 0) (bootsItem.maxDamage.toDouble() - bootsItem.damage.toDouble()) / (bootsItem.maxDamage.toDouble()) * 100.0 else 0

            context.drawText(Helix.MC.textRenderer, "${if (helmetPercent.toInt() > 0) helmetPercent.toInt() else "- "}%", originX+25, originY+5+4, Variables.guiColor, false)
            context.drawText(Helix.MC.textRenderer, "${if (chestPlatePercent.toInt() > 0) chestPlatePercent.toInt() else "- "}%", originX+25, originY+25+4, Variables.guiColor, false)
            context.drawText(Helix.MC.textRenderer, "${if (leggingsPercent.toInt() > 0) leggingsPercent.toInt() else "- "}%", originX+25, originY+45+4, Variables.guiColor, false)
            context.drawText(Helix.MC.textRenderer, "${if (bootsPercent.toInt() > 0) bootsPercent.toInt() else "- "}%", originX+25, originY+65+4, Variables.guiColor, false)
        } else {
            background.xSize = 75
            super.width = 75
            background.update(originX, originY).render(context, 0, 0, delta)

            context.drawItem(helmetItem, originX + 5, originY+5)
            context.drawItem(chestPlateItem, originX + 5, originY + 25)
            context.drawItem(leggingsItem, originX + 5, originY + 45)
            context.drawItem(bootsItem, originX + 5, originY + 65)

            context.drawText(Helix.MC.textRenderer, if (helmetItem.maxDamage > 0) "${helmetItem.maxDamage - helmetItem.damage}/${helmetItem.maxDamage}" else "-", originX+25, originY+5+4, Variables.guiColor, false)
            context.drawText(Helix.MC.textRenderer, if (chestPlateItem.maxDamage > 0) "${chestPlateItem.maxDamage - chestPlateItem.damage}/${chestPlateItem.maxDamage}" else "-", originX+25, originY+25+4, Variables.guiColor, false)
            context.drawText(Helix.MC.textRenderer, if (leggingsItem.maxDamage > 0) "${leggingsItem.maxDamage - leggingsItem.damage}/${leggingsItem.maxDamage}" else "-", originX+25, originY+45+4, Variables.guiColor, false)
            context.drawText(Helix.MC.textRenderer, if (bootsItem.maxDamage > 0) "${bootsItem.maxDamage - bootsItem.damage}/${bootsItem.maxDamage}" else "-", originX+25, originY+65+4, Variables.guiColor, false)
        }
    }
}