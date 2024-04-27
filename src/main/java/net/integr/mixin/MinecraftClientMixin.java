package net.integr.mixin;

import net.integr.Helix;
import net.integr.event.*;
import net.integr.eventsystem.EventSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void onTickPre(CallbackInfo ci) {
        PreTickEvent e = new PreTickEvent();
        UnsafePreTickEvent eu = new UnsafePreTickEvent();
        if (Helix.Companion.getMC().player != null) EventSystem.Companion.post(e);

        EventSystem.Companion.post(eu);

        if (e.isCancelled()) ci.cancel();
    }

    @Inject(method = "tick", at = @At("TAIL"), cancellable = true)
    public void onTickPost(CallbackInfo ci) {
        PostTickEvent e = new PostTickEvent();
        UnsafePostTickEvent eu = new UnsafePostTickEvent();
        if (Helix.Companion.getMC().player != null) EventSystem.Companion.post(e);
        EventSystem.Companion.post(eu);

        if (e.isCancelled()) ci.cancel();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    public void onStop(CallbackInfo ci) {
        EventSystem.Companion.post(new ClientEndEvent());
    }

    @Inject(method = "cleanUpAfterCrash", at = @At("HEAD"))
    public void onCrash(CallbackInfo ci) {
        EventSystem.Companion.post(new ClientEndEvent());
    }

    @Inject(method = "joinWorld", at = @At("HEAD"))
    public void onJoinWorld(ClientWorld world, CallbackInfo ci) {
        EventSystem.Companion.post(new JoinWorldEvent(world));
    }
}
