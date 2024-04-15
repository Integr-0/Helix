package net.integr.mixin;

import net.integr.event.UpdateLightMapTextureManagerEvent;
import net.integr.eventsystem.EventSystem;
import net.integr.modules.impl.FullbrightModule;
import net.integr.modules.management.ModuleManager;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Objects;

@Mixin(LightmapTextureManager.class)
public class LightMapTextureManagerMixin {
    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/NativeImage;setColor(III)V"))
    private void update(Args args) {
        UpdateLightMapTextureManagerEvent e = new UpdateLightMapTextureManagerEvent(args);
        EventSystem.Companion.post(e);
    }
}
