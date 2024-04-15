package net.integr.modules.impl

import net.integr.Helix
import net.integr.modules.management.UiModule
import net.integr.modules.management.settings.impl.BooleanSetting
import net.integr.rendering.uisystem.Box
import net.integr.rendering.uisystem.Slider
import net.minecraft.client.gui.DrawContext

class HotbarModule : UiModule("Hotbar", "Revamps the hotbar", "hotbar", 55, 95 + 95+5) {
    init {
        settings.add(BooleanSetting("Locked", "Lock the bars to the vanilla positions", "locked"))
    }

    private val healthBox = Box(0, 0, 95, 30, null, false)

    private val healthSlider = Slider(0, 0, 89, 20, " Health: ", false, "", 0.0, 20.0)
    private val armorSlider = Slider(0, 0, 89, 20, " Armor: ", false, "", 0.0, 20.0)

    private val foodBox = Box(0, 0, 95, 30, null, false)
    private val foodSlider = Slider(0, 0, 89, 20, " Food: ", false, "", 0.0, 20.0)

    private val airSlider = Slider(0, 0, 89, 20, " Air: ", false, "", 0.0, 300.0)

    override fun render(context: DrawContext, originX: Int, originY: Int, delta: Float) {
        if (settings.getById<BooleanSetting>("locked")!!.isEnabled()) {
            val width = context.scaledWindowWidth
            val height = context.scaledWindowHeight

            if (!Helix.MC.player!!.abilities.creativeMode && !Helix.MC.player!!.isSpectator) {
                if (Helix.MC.player!!.armor > 0) {
                    healthBox.ySize = 55
                    healthBox.update(width / 2 - 105, height - 95).render(context, 0, 0, delta)

                    armorSlider.setValue(Helix.MC.player!!.armor.toDouble())
                    armorSlider.update(width / 2 - 102, height - 90).render(context, 0, 0, delta)

                    healthSlider.max = Helix.MC.player!!.maxHealth.toDouble()+Helix.MC.player!!.absorptionAmount.toDouble()
                    healthSlider.setValue(Helix.MC.player!!.health.toDouble()+Helix.MC.player!!.absorptionAmount.toDouble())
                    healthSlider.update(width / 2 - 102, height - 65).render(context, 0, 0, delta)
                } else {
                    healthBox.ySize = 30
                    healthBox.update(width / 2 - 105, height - 70).render(context, 0, 0, delta)

                    healthSlider.max = Helix.MC.player!!.maxHealth.toDouble()+Helix.MC.player!!.absorptionAmount.toDouble()
                    healthSlider.setValue(Helix.MC.player!!.health.toDouble()+Helix.MC.player!!.absorptionAmount.toDouble())
                    healthSlider.update(width / 2 - 102, height - 65).render(context, 0, 0, delta)
                }

                if (Helix.MC.player!!.air < 300) {
                    foodBox.ySize = 55
                    foodBox.update(width / 2 + 11, height - 95).render(context, 0, 0, delta)

                    airSlider.setValue(Helix.MC.player!!.air.toDouble())
                    airSlider.update(width / 2 + 14, height - 90).render(context, 0, 0, delta)

                    foodSlider.setValue(Helix.MC.player!!.hungerManager.foodLevel.toDouble())
                    foodSlider.update(width / 2 + 14, height - 65).render(context, 0, 0, delta)
                } else {
                    foodBox.ySize = 30
                    foodBox.update(width / 2 + 11, height - 70).render(context, 0, 0, delta)

                    foodSlider.setValue(Helix.MC.player!!.hungerManager.foodLevel.toDouble())
                    foodSlider.update(width / 2 + 14, height - 65).render(context, 0, 0, delta)
                }
            }
        } else {
            if (!Helix.MC.player!!.abilities.creativeMode && !Helix.MC.player!!.isSpectator) {
                if (Helix.MC.player!!.armor > 0) {
                    healthBox.ySize = 55
                    healthBox.update(originX, originY).render(context, 0, 0, delta)

                    armorSlider.setValue(Helix.MC.player!!.armor.toDouble())
                    armorSlider.update(originX+3, originY + 5).render(context, 0, 0, delta)

                    healthSlider.max = Helix.MC.player!!.maxHealth.toDouble()+Helix.MC.player!!.absorptionAmount.toDouble()
                    healthSlider.setValue(Helix.MC.player!!.health.toDouble()+Helix.MC.player!!.absorptionAmount.toDouble())
                    healthSlider.update(originX+3, originY+30).render(context, 0, 0, delta)
                } else {
                    healthBox.ySize = 30
                    healthBox.update(originX, originY+25).render(context, 0, 0, delta)

                    healthSlider.max = Helix.MC.player!!.maxHealth.toDouble()+Helix.MC.player!!.absorptionAmount.toDouble()
                    healthSlider.setValue(Helix.MC.player!!.health.toDouble()+Helix.MC.player!!.absorptionAmount.toDouble())
                    healthSlider.update(originX+3, originY+30).render(context, 0, 0, delta)
                }

                if (Helix.MC.player!!.air < 300) {
                    foodBox.ySize = 55
                    foodBox.update(originX+100, originY).render(context, 0, 0, delta)

                    airSlider.setValue(Helix.MC.player!!.air.toDouble())
                    airSlider.update(originX+100+3, originY + 5).render(context, 0, 0, delta)

                    foodSlider.setValue(Helix.MC.player!!.hungerManager.foodLevel.toDouble())
                    foodSlider.update(originX+100+3, originY + 30).render(context, 0, 0, delta)
                } else {
                    foodBox.ySize = 30
                    foodBox.update(originX+100, originY+25).render(context, 0, 0, delta)

                    foodSlider.setValue(Helix.MC.player!!.hungerManager.foodLevel.toDouble())
                    foodSlider.update(originX+100+3, originY + 30).render(context, 0, 0, delta)
                }
            }
        }
    }
}