package net.integr.modules.management

import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder

class ModuleLoader {
    companion object {
        fun load(): List<Class<*>> {
            val l: MutableList<Class<*>> = mutableListOf()

            val reflections = Reflections(
                ConfigurationBuilder()
                    .forPackage("net.integr.modules.impl")
                    .filterInputsBy(FilterBuilder().includePackage("net.integr.modules.impl"))
            )

            reflections.getSubTypesOf(Module::class.java).forEach {
                if (it != UiModule::class.java) l += it
            }

            return l
        }
    }
}