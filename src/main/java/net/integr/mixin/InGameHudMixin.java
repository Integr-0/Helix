package net.integr.mixin;

import net.integr.Helix;
import net.integr.event.UiRenderEvent;
import net.integr.eventsystem.EventSystem;
import net.integr.modules.impl.HotbarModule;
import net.integr.modules.management.ModuleManager;
import net.integr.modules.management.settings.impl.BooleanSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow @Nullable private Text overlayMessage;
    @Unique Text uniqueOverlayMessage;
    @Shadow private int overlayRemaining;
    @Unique int uniqueOverlayRemaining;
    @Shadow private int scaledHeight;
    @Shadow private int scaledWidth;
    @Shadow @Final private MinecraftClient client;
    @Shadow private boolean overlayTinted;
    @Unique boolean uniqueOverlayTinted;
    @Shadow private void drawTextBackground(DrawContext context, TextRenderer textRenderer, int yOffset, int width, int color) {

    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void onRender(DrawContext context, float tickDelta, CallbackInfo ci) {
        UiRenderEvent e = new UiRenderEvent(context, tickDelta);
        EventSystem.Companion.post(e);

        if (e.isCancelled()) ci.cancel();

        if (Objects.requireNonNull(ModuleManager.Companion.getByClass(HotbarModule.class)).isEnabled() && ((BooleanSetting) Objects.requireNonNull(Objects.requireNonNull(ModuleManager.Companion.getByClass(HotbarModule.class)).getSettings().getById("locked"))).isEnabled()) {
            if (overlayMessage != null) {
                uniqueOverlayMessage = overlayMessage;
            }

            if(uniqueOverlayRemaining == 0) {
                uniqueOverlayMessage = overlayMessage;
            }

            uniqueOverlayRemaining = overlayRemaining;
            uniqueOverlayTinted = overlayTinted;
            overlayMessage = null;
            int m;
            int n;
            float h;
            int l;

            if (uniqueOverlayMessage != null && uniqueOverlayRemaining > 0) {
                client.getProfiler().push("overlayMessage");
                h = (float)uniqueOverlayRemaining - tickDelta;
                l = (int)(h * 255.0F / 20.0F);
                if (l > 255) {
                    l = 255;
                }

                if (l > 8) {
                    context.getMatrices().push();
                    context.getMatrices().translate((float)(scaledWidth / 2), (float)(scaledHeight - 68), 0.0F);
                    int k = 16777215;
                    if (uniqueOverlayTinted) {
                        k = MathHelper.hsvToRgb(h / 50.0F, 0.7F, 0.6F) & 16777215;
                    }

                    m = l << 24 & -16777216;
                    n = Helix.Companion.getMC().textRenderer.getWidth(uniqueOverlayMessage);
                    drawTextBackground(context, Helix.Companion.getMC().textRenderer, -4, n, 16777215 | m);
                    context.drawTextWithShadow(Helix.Companion.getMC().textRenderer, uniqueOverlayMessage, -n / 2, -14, k | m);
                    context.getMatrices().pop();
                }

                client.getProfiler().pop();
            }
        }
    }

    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    public void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        if (Objects.requireNonNull(ModuleManager.Companion.getByClass(HotbarModule.class)).isEnabled() && ((BooleanSetting) Objects.requireNonNull(Objects.requireNonNull(ModuleManager.Companion.getByClass(HotbarModule.class)).getSettings().getById("locked"))).isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    public void renderStatusBars(DrawContext context, CallbackInfo ci) {
        if (Objects.requireNonNull(ModuleManager.Companion.getByClass(HotbarModule.class)).isEnabled() && ((BooleanSetting) Objects.requireNonNull(Objects.requireNonNull(ModuleManager.Companion.getByClass(HotbarModule.class)).getSettings().getById("locked"))).isEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderHeldItemTooltip", at = @At("HEAD"), cancellable = true)
    public void renderHeldItemTooltip(DrawContext context, CallbackInfo ci) {
        if (Objects.requireNonNull(ModuleManager.Companion.getByClass(HotbarModule.class)).isEnabled() && ((BooleanSetting) Objects.requireNonNull(Objects.requireNonNull(ModuleManager.Companion.getByClass(HotbarModule.class)).getSettings().getById("locked"))).isEnabled()) {
            ci.cancel();
        }
    }
}
