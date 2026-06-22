package de.crafty.advancementprogress.mixin.client.multiplayer;

import de.crafty.advancementprogress.ClientAdvancementProgress;
import de.crafty.advancementprogress.util.AdvancementHelper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementTree;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(ClientAdvancements.class)
public abstract class MixinClientAdvancements {


    @Shadow
    @Final
    private Map<AdvancementHolder, AdvancementProgress> progress;

    @Shadow
    @Final
    private AdvancementTree tree;

    @Inject(method = "update", at = @At("RETURN"))
    private void onUpdate(ClientboundUpdateAdvancementsPacket packet, CallbackInfo ci){
        ClientAdvancementProgress.getInstance().getCompletedAdvancements().clear();

        this.tree.roots().forEach(advancementNode -> {
            Identifier id = advancementNode.holder().id();
            List<AdvancementNode> all = new ArrayList<>();
            AdvancementHelper.collectAll(advancementNode, all);

            int completed = all.stream().filter(node -> this.progress.containsKey(node.holder()) && this.progress.get(node.holder()).isDone()).toList().size();
            ClientAdvancementProgress.getInstance().getCompletedAdvancements().put(id, completed);
        });
    }

}
