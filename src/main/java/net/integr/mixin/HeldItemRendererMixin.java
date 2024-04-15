package net.integr.mixin;

import net.integr.event.RenderArmEvent;
import net.integr.event.RenderHeldItemEvent;
import net.integr.eventsystem.EventSystem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderArmHoldingItem(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IFFLnet/minecraft/util/Arm;)V"), cancellable = true)
    private void onRenderArm(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        RenderArmEvent e = new RenderArmEvent(player, tickDelta, pitch, hand, swingProgress, item, equipProgress, matrices, vertexConsumers, light);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) ci.cancel();
    }

    @Inject(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"), cancellable = true)
    private void onRenderItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        RenderHeldItemEvent e = new RenderHeldItemEvent(player, tickDelta, pitch, hand, swingProgress, item, equipProgress, matrices, vertexConsumers, light);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) ci.cancel();
    }
}
