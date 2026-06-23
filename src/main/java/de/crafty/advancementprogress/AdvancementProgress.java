package de.crafty.advancementprogress;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementProgress implements ModInitializer {

    public static final ResourceLocation TOTAL_ADVANCEMENTS_PACKET_ID = new ResourceLocation(AdvancementProgress.MOD_ID, "update_advancement_total");
    public static final ResourceLocation SAY_HELLO_PACKET_ID = new ResourceLocation(AdvancementProgress.MOD_ID, "say_hello");

    public static final String MOD_ID = "advancementprogress";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final String MOD_NAME = "AdvancementProgress";

    @Override
    public void onInitialize() {
        LOGGER.info("Greetings from AdvancementProgress!");
    }
}
