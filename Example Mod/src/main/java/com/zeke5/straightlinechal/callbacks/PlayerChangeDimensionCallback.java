package com.zeke5.straightlinechal.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;


public interface PlayerChangeDimensionCallback {
    Event<PlayerChangeDimensionCallback> EVENT = EventFactory.createArrayBacked(PlayerChangeDimensionCallback.class, (listeners)
            -> (player, server) -> {
        for (PlayerChangeDimensionCallback listener : listeners) {
            ActionResult result = listener.playerChangeDimension(player, server);

            if (result != ActionResult.PASS) {
                return result;
            }
        }

        return ActionResult.PASS;
    });

    ActionResult playerChangeDimension(ServerPlayerEntity player, ServerWorld server);
}