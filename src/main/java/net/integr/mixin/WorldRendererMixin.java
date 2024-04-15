package net.integr.mixin;

import net.integr.event.RenderEntityEvent;
import net.integr.event.SpawnParticleEvent;
import net.integr.eventsystem.EventSystem;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        RenderEntityEvent e = new RenderEntityEvent(entity, cameraX, cameraY, cameraZ, tickDelta, matrices, vertexConsumers);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) ci.cancel();
    }

    @Inject(method ="spawnParticle(Lnet/minecraft/particle/ParticleEffect;ZZDDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    public void spawnParticle(ParticleEffect parameters, boolean alwaysSpawn, boolean canSpawnOnMinimal, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {

        SpawnParticleEvent e = new SpawnParticleEvent(parameters, alwaysSpawn, canSpawnOnMinimal, x, y, z, velocityX, velocityY, velocityZ);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) cir.cancel(); else if (e.getCallback() != null) cir.setReturnValue((Particle) e.getCallback());
    }
}
