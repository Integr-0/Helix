package net.integr.mixin;

import net.integr.event.SwingHandEvent;
import net.integr.eventsystem.EventSystem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "swingHand", at = @At("HEAD"), cancellable = true)
    public void swingHand(Hand hand, CallbackInfo ci) {
        SwingHandEvent e = new SwingHandEvent(hand);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) {
            ci.cancel();
        }
    }
}
