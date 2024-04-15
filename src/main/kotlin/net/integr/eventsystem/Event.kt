package net.integr.eventsystem

open class Event {
    private var callback: Any? = null
    private var isCancelled: Boolean = false

    fun setCallback(v: Any?) {
        callback = v
    }

    fun cancel() {
        isCancelled = true
    }

    fun getCallback(): Any? = callback

    fun isCancelled() = isCancelled
}