package net.integr.utilities

import net.integr.Helix

class CommandSender {
    companion object {
        fun sendChatCommand(command: String?) {
            Helix.MC.networkHandler!!.sendChatCommand(command)
        }
    }
}