package net.integr.rendering.uisystem

import net.integr.rendering.uisystem.base.HelixUiElement
import kotlin.reflect.KClass

class UiLayout {
    private val l: MutableMap<HelixUiElement, Boolean> = mutableMapOf()

    fun add(obj: HelixUiElement): HelixUiElement {
        l[obj] = false

        return obj
    }

    fun clear() {
        l.clear()
    }

    fun onClick(mouseX: Double, mouseY: Double, button: Int) {
        for ((e, lo) in l) {
            if (lo) continue
            e.onClick(mouseX, mouseY, button)
        }
    }

    fun onRelease(mouseX: Double, mouseY: Double, button: Int) {
        for ((e, lo) in l) {
            if (lo) continue
            e.onRelease(mouseX, mouseY, button)
        }
    }

    fun removeAll(klass: KClass<*>) {
        for (e in l.keys) {
            if (e::class == klass) l.remove(e)
        }
    }

    fun lock(obj: HelixUiElement) {
        l[obj] = true
    }

    fun unLock(obj: HelixUiElement) {
        l[obj] = false
    }

    fun lockAll(klass: KClass<*>) {
        for (e in l.keys) {
            if (l::class == klass) lock(e)
        }
    }

    fun unLockAll(klass: KClass<*>) {
        for (e in l.keys) {
            if (l::class == klass) unLock(e)
        }
    }
}