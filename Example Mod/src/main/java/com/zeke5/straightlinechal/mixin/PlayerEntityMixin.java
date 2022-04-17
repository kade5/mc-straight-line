package com.zeke5.straightlinechal.mixin;

import com.zeke5.straightlinechal.StraightLineChallenge;
import com.zeke5.straightlinechal.util.IPlayerEntity;
import com.zeke5.straightlinechal.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements IPlayerEntity {

    public int getLineX() {
        PlayerEntity thisPlayer = (PlayerEntity)(Object)this;
        return ((WorldMixin) thisPlayer.world).getProperties().getSpawnX();
    }

    public int getLineY() {
        PlayerEntity thisPlayer = (PlayerEntity)(Object)this;
        return ((WorldMixin) thisPlayer.world).getProperties().getSpawnY();
    }

    public int getLineZ() {
        PlayerEntity thisPlayer = (PlayerEntity)(Object)this;
        return ((WorldMixin) thisPlayer.world).getProperties().getSpawnZ();
    }

    public int[] getNearestLinePosition(BlockPos blockPos) {

        int posX = blockPos.getX();
        int posY = blockPos.getY();
        int posZ = blockPos.getZ();

        if (Math.abs(posX - this.getLineX()) <= Math.abs(posZ - this.getLineZ())) {
            posX = this.getLineX();
        }
        else {
            posZ = this.getLineZ();
        }

        return new int [] {posX, posY, posZ};
    }

    public void doLineDamage() {
        Entity thisPlayer = (Entity)(Object) this;
        int[] linePosition = {this.getLineX(), this.getLineZ()};
        if (thisPlayer.getWorld().getRegistryKey() == World.OVERWORLD) {
            if (thisPlayer.getBlockX() != linePosition[0] && thisPlayer.getBlockZ() != linePosition[1] && thisPlayer.isAlive()) {
                this.invokeDamage(DamageSource.OUT_OF_WORLD, ModUtils.Factory.getOutOfLineDamage());
                StraightLineChallenge.LOGGER.debug("Player is taking damage");
            }
        } else if (thisPlayer.getWorld().getRegistryKey() == World.NETHER) {
            linePosition = ModUtils.Factory.convertToNetherCoords(linePosition);
            if (thisPlayer.getBlockX() != linePosition[0] && thisPlayer.getBlockZ() != linePosition[1] && thisPlayer.isAlive()) {
                this.invokeDamage(DamageSource.OUT_OF_WORLD, ModUtils.Factory.getOutOfLineDamage());
                StraightLineChallenge.LOGGER.debug("Player is taking damage");
            }
        }
    }


    @Invoker("damage")
    public abstract boolean invokeDamage(DamageSource source, float amount);

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void injectTick(CallbackInfo info) {
        //doLineDamage();
    }



}
