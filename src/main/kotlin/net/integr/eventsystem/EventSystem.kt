@file:Suppress("unused")

package net.integr.eventsystem

import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class EventSystem {
    companion object {
        private var receivers: MutableMap<KCallable<*>, Any> = mutableMapOf()

        fun register(klass: KClass<*>) {
            for (m in klass.members) {
                if (m.hasAnnotation<EventListen>()) {
                    receivers[m] = klass.createInstance()
                }
            }
        }

        fun register(klass: KClass<*>, instance: Any) {
            for (m in klass.members) {
                if (m.hasAnnotation<EventListen>()) {
                    receivers[m] = instance
                }
            }
        }

        fun register(instance: Any) {
            for (m in instance::class.members) {
                if (m.hasAnnotation<EventListen>()) {
                    receivers[m] = instance
                }
            }
        }

        fun unRegister(klass: KClass<*>) {
            for (m in klass.members) {
                if (m.hasAnnotation<EventListen>()) {
                    receivers.remove(m)
                }
            }
        }

        fun unRegister(instance: Any) {
            for (m in instance::class.members) {
                if (m.hasAnnotation<EventListen>()) {
                    receivers.remove(m)
                }
            }
        }

        fun post(event: Event): Any? {
            for (m in receivers.keys.sortedBy { it.findAnnotation<EventListen>()?.prio }) {
                if (m.parameters.size > 2) throw IllegalArgumentException("Only one parameter is allowed at ${m.javaClass.declaringClass.kotlin.simpleName}.${m.name}()")

                if (m.parameters[1].type.toString().substringAfterLast('.') == event::class.simpleName) {
                    m.call(receivers[m], event)
                    if (event.isCancelled()) break
                }
            }

            return event.getCallback()
        }
    }
}