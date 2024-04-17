package net.integr.rendering.uisystem

import net.integr.rendering.uisystem.base.HelixUiElement

class UiLayout {
    private val l: MutableList<HelixUiElement> = mutableListOf()

    fun add(obj: HelixUiElement): HelixUiElement {
        l += obj

        return obj
    }

    fun clear() {
        l.clear()
    }

    fun onClick(mouseX: Double, mouseY: Double, button: Int) {
        for (e in l) {
            e.onClick(mouseX, mouseY, button)
        }
    }

    fun onRelease(mouseX: Double, mouseY: Double, button: Int) {
        for (e in l) {
            e.onRelease(mouseX, mouseY, button)
        }
    }
}