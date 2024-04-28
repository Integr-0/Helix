package net.integr.mixin;

import net.integr.event.BlockBreakEvent;
import net.integr.eventsystem.EventSystem;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"), cancellable = true)
    public void onUpdateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        BlockBreakEvent e = new BlockBreakEvent(pos, direction);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) {
            cir.setReturnValue(false);
        }
    }
}
