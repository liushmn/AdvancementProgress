package de.crafty.advancementprogress.network;

import de.crafty.advancementprogress.AdvancementProgress;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A payload used to inform the client about the total amount of advancements mapped by its category
 * @param total The map of advancement categories and their total amount of advancements (categoryId is represented by the id of the root element)
 */
public record ClientboundUpdateAdvancementTotalPayload(Map<Identifier, Integer> total) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientboundUpdateAdvancementTotalPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(AdvancementProgress.MOD_ID, "update_advancement_total"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUpdateAdvancementTotalPayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG, ClientboundUpdateAdvancementTotalPayload::toTag, ClientboundUpdateAdvancementTotalPayload::fromTag

    );

    public static CompoundTag toTag(ClientboundUpdateAdvancementTotalPayload payload){
        CompoundTag tag = new CompoundTag();
        payload.total().forEach((id, value) -> tag.putInt(id.toString(), value));
        return tag;
    }

    public static ClientboundUpdateAdvancementTotalPayload fromTag(CompoundTag tag){
        Map<Identifier, Integer> map = new HashMap<>();
        tag.keySet().forEach(id -> {
            map.put(Identifier.tryParse(id), tag.getIntOr(id, -1));
        });
        return new ClientboundUpdateAdvancementTotalPayload(map);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
