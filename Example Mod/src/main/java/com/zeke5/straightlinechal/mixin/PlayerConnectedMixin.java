package com.zeke5.straightlinechal.mixin;

import com.zeke5.straightlinechal.callbacks.PlayerConnectedCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class PlayerConnectedMixin {

    @Inject(at = @At(value = "TAIL"), method = "onPlayerConnected", cancellable = true)
    private void injectOnPlayerConnected(ServerPlayerEntity player, CallbackInfo info) {
        PlayerConnectedCallback.EVENT.invoker().playerConnected(player, (ServerWorld)(Object)this);
    }
}
