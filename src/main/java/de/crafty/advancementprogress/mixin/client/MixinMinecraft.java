package de.crafty.advancementprogress.mixin.client;

import de.crafty.advancementprogress.AdvancementProgress;
import de.crafty.advancementprogress.ClientAdvancementProgress;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {


    @Shadow
    @Final
    public Gui gui;

    /**
     * When the client did not receive the serverside hello packet after 5 seconds, it is assumed
     * that the mod is not installed on the server
     */
    @Inject(method = "tick", at = @At("RETURN"))
    private void checkForInstallation(CallbackInfo ci){
        if(System.currentTimeMillis() - ClientAdvancementProgress.getInstance().getLastConnected() > 5000L && !ClientAdvancementProgress.getInstance().isInstalledOnServer()){
            this.gui.getChat().addMessage(
                    (Component.literal("[").withStyle(ChatFormatting.GRAY))
                            .append(Component.literal(AdvancementProgress.MOD_NAME).withStyle(ChatFormatting.GOLD))
                            .append(Component.literal("] ").withStyle(ChatFormatting.GRAY))
                            .append(Component.translatable("advancementprogress.not_installed").withStyle(ChatFormatting.RED))
            );
            ClientAdvancementProgress.getInstance().setLastConnected(Long.MAX_VALUE);
        }

    }
}
