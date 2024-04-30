package net.integr.utilities

import net.integr.Helix
import net.minecraft.network.encryption.NetworkEncryptionUtils
import net.minecraft.network.message.LastSeenMessagesCollector
import net.minecraft.network.message.LastSeenMessagesCollector.LastSeenMessages
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket
import java.time.Instant


class CommandSender {
    companion object {
        fun sendChatCommand(command: String?) {
            val instant: Instant = Instant.now()
            val l = NetworkEncryptionUtils.SecureRandomUtil.nextLong()
            val lastSeenMessages: LastSeenMessages = LastSeenMessagesCollector(0).collect()

            Helix.MC.networkHandler!!.sendPacket(CommandExecutionC2SPacket(command, instant, l, null, lastSeenMessages.update))
        }
    }
}