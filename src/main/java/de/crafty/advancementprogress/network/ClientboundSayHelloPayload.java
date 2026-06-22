package de.crafty.advancementprogress.network;

import de.crafty.advancementprogress.AdvancementProgress;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

/**
 * A payload used to inform the client that the mod exists on the server
 */
public record ClientboundSayHelloPayload() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientboundSayHelloPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AdvancementProgress.MOD_ID, "say_hello"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSayHelloPayload> CODEC = StreamCodec.unit(new ClientboundSayHelloPayload());


    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
