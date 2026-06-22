package de.crafty.advancementprogress.mixin.server.players;

import de.crafty.advancementprogress.network.ClientboundUpdateAdvancementTotalPayload;
import de.crafty.advancementprogress.util.AdvancementHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
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
    private Map<UUID, PlayerAdvancements> advancements;

    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    @Final
    private List<ServerPlayer> players;

    @Inject(method = "placeNewPlayer", at = @At("RETURN"))
    private void updateTotalAdvancements(Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
        ServerPlayNetworking.send(player, new ClientboundUpdateAdvancementTotalPayload(AdvancementHelper.createTotalMap(this.server)));
    }

    @Inject(method = "reloadResources", at = @At("RETURN"))
    private void reloadTotalAdvancements(CallbackInfo ci){
        this.players.forEach(player -> {
            ServerPlayNetworking.send(player, new ClientboundUpdateAdvancementTotalPayload(AdvancementHelper.createTotalMap(this.server)));
        });

    }

}
