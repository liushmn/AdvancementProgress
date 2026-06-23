package de.crafty.advancementprogress.mixin.client.multiplayer;

import de.crafty.advancementprogress.ClientAdvancementProgress;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener implements TickablePacketListener, ClientGamePacketListener {

    /**
     * Sets the last connected timestamp when the client logs into a new server
     */
    @Inject(method = "handleLogin", at = @At("RETURN"))
    private void setTimestamp(ClientboundLoginPacket packet, CallbackInfo ci){
        ClientAdvancementProgress.getInstance().setLastConnected(System.currentTimeMillis());
    }
}
