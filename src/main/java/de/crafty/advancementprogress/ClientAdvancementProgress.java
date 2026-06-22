package de.crafty.advancementprogress;

import de.crafty.advancementprogress.network.ClientboundSayHelloPayload;
import de.crafty.advancementprogress.network.ClientboundUpdateAdvancementTotalPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ClientAdvancementProgress implements ClientModInitializer {

    private static ClientAdvancementProgress instance;

    /**
     * Server connection specific data to track serverside mod presence
     */
    private boolean installedOnServer = false;
    private long lastConnected = Long.MAX_VALUE;

    private final Map<Identifier, Integer> totalAdvancements = new HashMap<>();
    private final Map<Identifier, Integer> completedAdvancements = new HashMap<>();

    @Override
    public void onInitializeClient() {
        instance = this;

        ClientPlayNetworking.registerGlobalReceiver(ClientboundUpdateAdvancementTotalPayload.TYPE, (payload, context) -> {
            this.totalAdvancements.clear();
            this.totalAdvancements.putAll(payload.total());
        });

        ClientPlayNetworking.registerGlobalReceiver(ClientboundSayHelloPayload.TYPE, (payload, context) -> {
            this.installedOnServer = true;
        });
    }

    public Map<Identifier, Integer> getTotalAdvancements() {
        return this.totalAdvancements;
    }

    public Map<Identifier, Integer> getCompletedAdvancements() {
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
