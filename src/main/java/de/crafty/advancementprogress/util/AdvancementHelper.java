package de.crafty.advancementprogress.util;

import net.minecraft.advancements.AdvancementNode;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;

import java.util.*;

public class AdvancementHelper {


    public static Map<Identifier, Integer> createTotalMap(MinecraftServer server){
        Map<Identifier, Integer> total = new HashMap<>();

        server.getAdvancements().tree().roots().forEach(advancementNode -> {
            Identifier id = advancementNode.holder().id();

            List<AdvancementNode> all = new ArrayList<>();
            AdvancementHelper.collectAll(advancementNode, all);
            if(!all.isEmpty())
                total.put(id, all.size());

        });
        return total;
    }

    public static void collectAll(AdvancementNode current, List<AdvancementNode> list){
        Iterator<AdvancementNode> iterator = current.children().iterator();
        List<AdvancementNode> children = new ArrayList<>();
        while (iterator.hasNext()){
            AdvancementNode child = iterator.next();
            if(child.advancement().display().isPresent())
                children.add(child);
        }
        list.addAll(children);
        children.forEach(child -> collectAll(child, list));
    }

}
