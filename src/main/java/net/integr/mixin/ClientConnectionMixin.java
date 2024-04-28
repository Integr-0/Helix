package net.integr.mixin;

import net.integr.event.ReceivePacketEvent;
import net.integr.event.SendPacketEvent;
import net.integr.eventsystem.EventSystem;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static void onPacket(Packet<?> packet, PacketListener listener, CallbackInfo ci) {
        ReceivePacketEvent e = new ReceivePacketEvent(packet);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) ci.cancel();
    }

    @Inject(method = "sendInternal", at = @At("HEAD"), cancellable = true)
    private void onPacketSend(Packet<?> packet, PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        SendPacketEvent e = new SendPacketEvent(packet);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) ci.cancel();
    }
}
