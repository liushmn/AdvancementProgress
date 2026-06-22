package de.crafty.advancementprogress.mixin.client.gui.screens.advancements;

import de.crafty.advancementprogress.ClientAdvancementProgress;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AdvancementsScreen.class)
public abstract class MixinAdvancementsScreen extends Screen implements ClientAdvancements.Listener {

    @Shadow
    private @Nullable AdvancementTab selectedTab;

    @Shadow
    @Final
    public static int WINDOW_WIDTH;

    protected MixinAdvancementsScreen(Component title) {
        super(title);
    }


    /**
     * Renders the progress in the advancement's screen
     */
    @Inject(method = "extractWindow", at = @At("RETURN"))
    private void renderProgress(GuiGraphicsExtractor graphics, int xo, int yo, int mouseX, int mouseY, CallbackInfo ci){
        if(this.selectedTab == null) return;

        if(ClientAdvancementProgress.getInstance().getTotalAdvancements().isEmpty()) return;

        Component progressComp = Component.literal(ClientAdvancementProgress.getInstance().getCompletedAdvancements().get(this.selectedTab.getRootNode().holder().id()) + "/" + ClientAdvancementProgress.getInstance().getTotalAdvancements().get(this.selectedTab.getRootNode().holder().id()));
        graphics.text(this.font, progressComp, xo + WINDOW_WIDTH - this.font.width(progressComp) - 8, yo + 6, -12566464, false);
    }
}
