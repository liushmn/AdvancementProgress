package de.crafty.advancementprogress.util;

import de.crafty.advancementprogress.mixin.server.players.ServerAdvancementManagerAccessor;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

import java.util.*;

public class AdvancementHelper {


    public static FriendlyByteBuf createEncodedTotalMap(MinecraftServer server){
        FriendlyByteBuf byteBuf = PacketByteBufs.create();
        Map<ResourceLocation, Integer> total = createTotalMap(server);
        byteBuf.writeMap(total, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeInt);
        return byteBuf;
    }

    /**
     * Creates a map containing all advancement categories with its total amount of advancements
     */
    public static Map<ResourceLocation, Integer> createTotalMap(MinecraftServer server){
        Map<ResourceLocation, Integer> total = new HashMap<>();

        ((ServerAdvancementManagerAccessor)server.getAdvancements()).advancementList().getRoots().forEach(advancement -> {
            ResourceLocation id = advancement.getId();

            List<Advancement> all = new ArrayList<>();
            AdvancementHelper.collectAll(advancement, all);
            if(!all.isEmpty())
                total.put(id, all.size());

        });
        return total;
    }

    /**
     * Collects all advancements into a list starting with a given node
     * @param current
     * @param list
     */
    public static void collectAll(Advancement current, List<Advancement> list){
        Iterator<Advancement> iterator = current.getChildren().iterator();
        List<Advancement> children = new ArrayList<>();
        while (iterator.hasNext()){
            Advancement child = iterator.next();
            if(child.getDisplay() != null)
                children.add(child);
        }
        list.addAll(children);
        children.forEach(child -> collectAll(child, list));
    }

}
