package com.zeke5.straightlinechal.mixin;

import com.zeke5.straightlinechal.StraightLineChallenge;
import com.zeke5.straightlinechal.util.IPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements IPlayerEntity {
    private int lineX = 0;
    private int lineZ = 0;
    private float outOfLineDamage = 0.5f;

    public void setLineCords(BlockPos blockPos) {
        this.lineX = blockPos.getX();
        this.lineZ = blockPos.getZ();
        StraightLineChallenge.LOGGER.info("Straight line Coords set to (" + this.lineX + ", " + this.lineZ + ")");
    }


    @Invoker("damage")
    public abstract boolean invokeDamage(DamageSource source, float amount);

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void injectTick(CallbackInfo info) {
        Entity thisPlayer = (Entity)(Object) this;
        if (thisPlayer.getBlockX() != this.lineX && thisPlayer.getBlockZ() != this.lineZ && thisPlayer.isAlive()) {
            this.invokeDamage(DamageSource.OUT_OF_WORLD, this.outOfLineDamage);
            StraightLineChallenge.LOGGER.debug("Player is taking damage");
        }
    }



}
