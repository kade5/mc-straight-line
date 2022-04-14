package com.zeke5.straightlinechal.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;


public interface PlayerConnectedCallback {
    Event<PlayerConnectedCallback> EVENT = EventFactory.createArrayBacked(PlayerConnectedCallback.class, (listeners)
            -> (player, server) -> {
        for (PlayerConnectedCallback listener : listeners) {
            ActionResult result = listener.playerConnected(player, server);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        return ActionResult.PASS;
    });

    ActionResult playerConnected(ServerPlayerEntity player, ServerWorld server);
}