package de.crafty.advancementprogress;

import de.crafty.advancementprogress.network.ClientboundUpdateAdvancementTotalPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ClientAdvancementProgress implements ClientModInitializer {

    private static ClientAdvancementProgress instance;
    private final Map<Identifier, Integer> totalAdvancements = new HashMap<>();
    private final Map<Identifier, Integer> completedAdvancements = new HashMap<>();

    @Override
    public void onInitializeClient() {
        instance = this;

        ClientPlayNetworking.registerGlobalReceiver(ClientboundUpdateAdvancementTotalPayload.TYPE, (payload, context) -> {
            this.totalAdvancements.clear();
            this.totalAdvancements.putAll(payload.total());
            System.out.println("Received total advancement types: " + this.totalAdvancements.size());
            this.totalAdvancements.forEach((id, value) -> System.out.println(id + ": " + value));
        });
    }

    public Map<Identifier, Integer> getTotalAdvancements() {
        return this.totalAdvancements;
    }

    public Map<Identifier, Integer> getCompletedAdvancements() {
        return this.completedAdvancements;
    }

    public static ClientAdvancementProgress getInstance() {
        return instance;
    }
}
