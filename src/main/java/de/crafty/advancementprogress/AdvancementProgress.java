package de.crafty.advancementprogress;

import de.crafty.advancementprogress.network.ClientboundUpdateAdvancementTotalPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementProgress implements ModInitializer {

    public static final String MOD_ID = "advancementprogress";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Greetings from AdvancementProgress!");

        PayloadTypeRegistry.clientboundPlay().register(ClientboundUpdateAdvancementTotalPayload.TYPE, ClientboundUpdateAdvancementTotalPayload.CODEC);
    }
}
