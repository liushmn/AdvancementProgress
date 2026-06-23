package de.crafty.advancementprogress.mixin.client.multiplayer;

import de.crafty.advancementprogress.ClientAdvancementProgress;
import de.crafty.advancementprogress.util.AdvancementHelper;
import net.minecraft.advancements.*;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
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
    private Map<Advancement, AdvancementProgress> progress;
    @Shadow
    @Final
    private AdvancementList advancements;


    /**
     * Updates completed advancements clientside
     */
    @Inject(method = "update", at = @At("RETURN"))
    private void onUpdate(ClientboundUpdateAdvancementsPacket packet, CallbackInfo ci){
        ClientAdvancementProgress.getInstance().getCompletedAdvancements().clear();

        this.advancements.getRoots().forEach(advancement -> {
            ResourceLocation id = advancement.getId();
            List<Advancement> all = new ArrayList<>();
            AdvancementHelper.collectAll(advancement, all);

            int completed = all.stream().filter(node -> this.progress.containsKey(node) && this.progress.get(node).isDone()).toList().size();
            ClientAdvancementProgress.getInstance().getCompletedAdvancements().put(id, completed);
        });
    }

}
