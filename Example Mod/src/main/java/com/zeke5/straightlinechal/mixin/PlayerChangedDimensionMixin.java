package com.zeke5.straightlinechal.mixin;

import com.zeke5.straightlinechal.callbacks.PlayerChangeDimensionCallback;
import com.zeke5.straightlinechal.callbacks.PlayerConnectedCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class PlayerChangedDimensionMixin {

    @Inject(at = @At(value = "TAIL"), method = "onPlayerChangeDimension", cancellable = true)
    private void injectOnPlayerChangeDimension(ServerPlayerEntity player, CallbackInfo info) {
        PlayerChangeDimensionCallback.EVENT.invoker().playerChangeDimension(player, (ServerWorld)(Object)this);
    }
}
