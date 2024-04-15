package net.integr.mixin;

import net.integr.event.EntityHasLabelEvent;
import net.integr.eventsystem.EventSystem;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(at = @At("HEAD"), method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", cancellable = true)
    public void hasLabel(LivingEntity entity, CallbackInfoReturnable<Boolean> ci) {
        EntityHasLabelEvent e = new EntityHasLabelEvent(entity);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) ci.cancel(); else if (e.getCallback() != null) ci.setReturnValue((Boolean) e.getCallback());
    }
}
