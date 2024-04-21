package net.integr.mixin;

import com.mojang.brigadier.ParseResults;
import net.integr.Helix;
import net.integr.event.SendChatMessageEvent;
import net.integr.event.SendCommandEvent;
import net.integr.eventsystem.EventSystem;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.SignedArgumentList;
import net.minecraft.network.encryption.NetworkEncryptionUtils;
import net.minecraft.network.message.*;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;
import java.util.Objects;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Shadow private MessageChain.Packer messagePacker;
    @Shadow private LastSeenMessagesCollector lastSeenMessagesCollector;


    @Shadow protected abstract ParseResults<CommandSource> parse(String command);

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onChatMessage(String message, CallbackInfo ci) {
        SendChatMessageEvent e = new SendChatMessageEvent(message);
        EventSystem.Companion.post(e);

        if (e.getCallback() != null) {
            message = (String) e.getCallback();
            ci.cancel();

            sendChatMessageU(message);
        } else if (e.isCancelled()) ci.cancel();
    }

    @Unique
    public void sendChatMessageU(String content) {
        Instant instant = Instant.now();
        long l = NetworkEncryptionUtils.SecureRandomUtil.nextLong();
        LastSeenMessagesCollector.LastSeenMessages lastSeenMessages = this.lastSeenMessagesCollector.collect();
        MessageSignatureData messageSignatureData = messagePacker.pack(new MessageBody(content, instant, l, lastSeenMessages.lastSeen()));
        Objects.requireNonNull(Helix.Companion.getMC().getNetworkHandler()).sendPacket(new ChatMessageC2SPacket(content, instant, l, messageSignatureData, lastSeenMessages.update()));
    }

    @Inject(method = "sendChatCommand", at = @At("HEAD"), cancellable = true)
    private void onCommand(String command, CallbackInfo ci) {
        SendCommandEvent e = new SendCommandEvent(command);
        EventSystem.Companion.post(e);

        if (e.getCallback() != null) {
            command = (String) e.getCallback();
            ci.cancel();

            sendChatCommandU(command);
        } else if (e.isCancelled()) ci.cancel();
    }

    @Unique
    public void sendChatCommandU(String command) {
        Instant instant = Instant.now();
        long l = NetworkEncryptionUtils.SecureRandomUtil.nextLong();
        LastSeenMessagesCollector.LastSeenMessages lastSeenMessages = this.lastSeenMessagesCollector.collect();
        ArgumentSignatureDataMap argumentSignatureDataMap = ArgumentSignatureDataMap.sign(SignedArgumentList.of(parse(command)), (value) -> {
            MessageBody messageBody = new MessageBody(value, instant, l, lastSeenMessages.lastSeen());
            return this.messagePacker.pack(messageBody);
        });
        Objects.requireNonNull(Helix.Companion.getMC().getNetworkHandler()).sendPacket(new CommandExecutionC2SPacket(command, instant, l, argumentSignatureDataMap, lastSeenMessages.update()));
    }
}
