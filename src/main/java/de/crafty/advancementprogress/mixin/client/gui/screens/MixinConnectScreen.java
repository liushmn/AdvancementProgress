package de.crafty.advancementprogress.mixin.client.gui.screens;

import de.crafty.advancementprogress.ClientAdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public abstract class MixinConnectScreen extends Screen {


    protected MixinConnectScreen(Component title) {
        super(title);
    }


    /**
     * Resets all server-specific data when connecting to a new server
     */
    @Inject(method = "startConnecting", at = @At("HEAD"))
    private static void resetMaps(Screen parent, Minecraft minecraft, ServerAddress hostAndPort, ServerData data, boolean isQuickPlay, TransferState transferState, CallbackInfo ci){
        ClientAdvancementProgress.getInstance().resetInstalledOnServer();
        ClientAdvancementProgress.getInstance().getTotalAdvancements().clear();
        ClientAdvancementProgress.getInstance().getCompletedAdvancements().clear();
    }
}
