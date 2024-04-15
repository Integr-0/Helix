package net.integr.mixin;

import net.integr.Helix;
import net.integr.Variables;
import net.integr.event.RenderTitleScreenEvent;
import net.integr.eventsystem.EventSystem;
import net.integr.rendering.uisystem.Box;
import net.integr.rendering.uisystem.Button;
import net.integr.rendering.uisystem.UiLayout;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.AccessibilityOnboardingButtons;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsNotificationsScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(at = @At("TAIL"), method = "render", cancellable = true)
	private void renderTitleScreen(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		RenderTitleScreenEvent e = new RenderTitleScreenEvent(context, mouseX, mouseY, delta);
		EventSystem.Companion.post(e);

		if (e.isCancelled()) ci.cancel();
	}
}