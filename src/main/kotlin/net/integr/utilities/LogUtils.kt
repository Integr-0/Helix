package net.integr.utilities

import net.integr.Helix
import net.minecraft.text.Text
import net.minecraft.util.Formatting

class LogUtils {
    companion object {
        fun sendChatLog(str: String) {
            Helix.MC.player!!.sendMessage(Text.literal("" + Formatting.GOLD + "[Helix]" + Formatting.DARK_GRAY + " » " + Formatting.GRAY + str))
        }

        fun sendLog(str: String) {
            Helix.LOGGER.info("[Helix] » $str")
        }
    }
}