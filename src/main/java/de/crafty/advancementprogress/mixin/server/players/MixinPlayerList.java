package de.crafty.advancementprogress.mixin.server.players;

import de.crafty.advancementprogress.AdvancementProgress;
import de.crafty.advancementprogress.util.AdvancementHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(PlayerList.class)
public abstract class MixinPlayerList {


    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    @Final
    private List<ServerPlayer> players;

    /**
     * Sends the total advancement map to a player after login

     */
    @Inject(method = "placeNewPlayer", at = @At("RETURN"))
    private void updateTotalAdvancements(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
        ServerPlayNetworking.send(serverPlayer, AdvancementProgress.SAY_HELLO_PACKET_ID, PacketByteBufs.create());
        ServerPlayNetworking.send(serverPlayer, AdvancementProgress.TOTAL_ADVANCEMENTS_PACKET_ID, AdvancementHelper.createEncodedTotalMap(this.server));
    }

    /**
     * Sends the total advancement map to all players when the server is reloaded
     */
    @Inject(method = "reloadResources", at = @At("RETURN"))
    private void reloadTotalAdvancements(CallbackInfo ci){
        this.players.forEach(player -> {
            ServerPlayNetworking.send(player, AdvancementProgress.TOTAL_ADVANCEMENTS_PACKET_ID, AdvancementHelper.createEncodedTotalMap(this.server));
        });
    }


}
