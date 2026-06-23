package de.crafty.advancementprogress;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ClientAdvancementProgress implements ClientModInitializer {

    private static ClientAdvancementProgress instance;

    /**
     * Server connection specific data to track serverside mod presence
     */
    private boolean installedOnServer = false;
    private long lastConnected = Long.MAX_VALUE;

    private final Map<ResourceLocation, Integer> totalAdvancements = new HashMap<>();
    private final Map<ResourceLocation, Integer> completedAdvancements = new HashMap<>();

    @Override
    public void onInitializeClient() {
        instance = this;

        ClientPlayNetworking.registerGlobalReceiver(AdvancementProgress.TOTAL_ADVANCEMENTS_PACKET_ID, (client, handler, buf, responseSender) -> {
            this.totalAdvancements.clear();
            this.totalAdvancements.putAll(buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readInt));
        });

        ClientPlayNetworking.registerGlobalReceiver(AdvancementProgress.SAY_HELLO_PACKET_ID, (client, handler, buf, responseSender) ->  {
            this.installedOnServer = true;
        });
    }

    public Map<ResourceLocation, Integer> getTotalAdvancements() {
        return this.totalAdvancements;
    }

    public Map<ResourceLocation, Integer> getCompletedAdvancements() {
        return this.completedAdvancements;
    }

    public boolean isInstalledOnServer() {
        return this.installedOnServer;
    }

    public long getLastConnected() {
        return this.lastConnected;
    }

    public void resetInstalledOnServer() {
        this.installedOnServer = false;
        this.lastConnected = Long.MAX_VALUE;
    }

    public void setLastConnected(long lastConnected) {
        this.lastConnected = lastConnected;
    }

    public static ClientAdvancementProgress getInstance() {
        return instance;
    }
}
