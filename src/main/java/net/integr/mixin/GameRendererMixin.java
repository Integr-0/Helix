package net.integr.mixin;

import net.integr.event.RenderWorldEvent;
import net.integr.eventsystem.EventSystem;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = Opcodes.GETFIELD, ordinal = 0), method = "renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V", cancellable = true)
    public void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        RenderWorldEvent e = new RenderWorldEvent(tickDelta, matrices);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) ci.cancel();
    }
}
