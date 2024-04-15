package net.integr.eventsystem

@Target(AnnotationTarget.FUNCTION)
annotation class EventListen(val prio: Priority = Priority.NORMAL)
