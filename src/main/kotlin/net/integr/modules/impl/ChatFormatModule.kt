@file:Suppress("DuplicatedCode")

package net.integr.modules.impl

import net.integr.Helix
import net.integr.event.SendChatMessageEvent
import net.integr.event.SendCommandEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.management.Module
import net.minecraft.client.gui.DrawContext
import java.awt.Color
import kotlin.math.*


class ChatFormatModule : Module("Chat Format", "Chat formatter (Details on the Modrinth page)", "chatFormat"){
    @EventListen
    fun onChatMessage(event: SendChatMessageEvent) {
        event.callback = replaceInText(event.message)
    }

    @EventListen
    fun onCommand(event: SendCommandEvent) {
        event.callback = replaceInText(event.command)
    }

    private fun replaceInText(messageIn: String): String {
        var message = messageIn

        Regex("(<.*:([lonm]*)#[0-9a-f]{6}>)[^<]*(<#[0-9a-f]{6}>)").findAll(messageIn).forEach { exp ->
            var modText = ""
            val mods = exp.value.substring(exp.value.indexOf(':')+1..<exp.value.indexOf('#'))
            val fChar = exp.value.substring(1..<exp.value.indexOf(':'))

            mods.forEach {
                modText += "&$it"
            }

            val c1 = exp.value.substring(exp.value.indexOf('<')+1+mods.count()+fChar.count()+1..<exp.value.indexOf('>'))
            val c2 = exp.value.substring(exp.value.lastIndexOf('<')+1..<exp.value.lastIndexOf('>'))

            val text = exp.value.substring(exp.value.indexOf('>')+1..<exp.value.lastIndexOf('<'))

            val hText = grad(Color.decode(c1), Color.decode(c2), text, fChar, modText)
            message = message.replaceRange(exp.range.first, exp.range.last+1, hText)
        }

        return message
    }

    private fun grad(color1: Color, color2: Color, word: String, fChar: String, modifiers: String): String {
        val r1 = color1.red
        val g1 = color1.green
        val b1 = color1.blue
        val r2 = color2.red
        val g2 = color2.green
        val b2 = color2.blue

        val unitVector = intArrayOf(r2 - r1, g2 - g1, b2 - b1)
        val r = unitVector[0].toDouble() / word.length
        val g = unitVector[1].toDouble() / word.length
        val b = unitVector[2].toDouble() / word.length

        val values = intArrayOf(r1, g1, b1)
        val output: MutableList<String> = ArrayList()
        for (i in word.indices) {
            if (word[i] == ' ') {
                output += " "

                values[0] = (values[0] + r).toInt()
                values[1] = (values[1] + g).toInt()
                values[2] = (values[2] + b).toInt()
            } else {
                output += "$fChar#" + componentToHex(values[0]) + componentToHex(values[1]) + componentToHex(values[2]) + modifiers + word[i]

                values[0] = (values[0] + r).toInt()
                values[1] = (values[1] + g).toInt()
                values[2] = (values[2] + b).toInt()
            }
        }

        return output.joinToString(separator = "")
    }

    private fun componentToHex(c: Int): String {
        val hex = Integer.toHexString(c)
        return if (hex.length == 1) "0$hex" else hex
    }
}