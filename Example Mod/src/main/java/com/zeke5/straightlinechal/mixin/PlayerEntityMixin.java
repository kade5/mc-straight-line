package com.zeke5.straightlinechal.mixin;

import com.zeke5.straightlinechal.StraightLineChallenge;
import com.zeke5.straightlinechal.manager.StraightLineManager;
import com.zeke5.straightlinechal.util.IPlayerEntity;
import com.zeke5.straightlinechal.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements IPlayerEntity {


    public void doLineDamage() {
        Entity thisPlayer = (Entity)(Object) this;
        int[] linePosition = StraightLineManager.Manage.getINSTANCE().getOverworldLine();
        if (thisPlayer.getWorld().getRegistryKey() == World.OVERWORLD) {
            if (thisPlayer.getBlockX() != linePosition[0] && thisPlayer.getBlockZ() != linePosition[1] && thisPlayer.isAlive()) {
                this.invokeDamage(DamageSource.OUT_OF_WORLD, ModUtils.Factory.getOutOfLineDamage());
                StraightLineChallenge.LOGGER.debug("Player is taking damage from being out of line");
            }
        } else if (thisPlayer.getWorld().getRegistryKey() == World.NETHER) {
            linePosition =  StraightLineManager.Manage.getINSTANCE().getNetherLine();
            if (thisPlayer.getBlockX() != linePosition[0] && thisPlayer.getBlockZ() != linePosition[1] && thisPlayer.isAlive()) {
                this.invokeDamage(DamageSource.OUT_OF_WORLD, ModUtils.Factory.getOutOfLineDamage());
                StraightLineChallenge.LOGGER.debug("Player is taking damage from being out of line");
            }
        }
    }

    public boolean canTakeLineDamage() {
        Entity thisPlayer = (PlayerEntity)(Object) this;
        MinecraftServer server = thisPlayer.world.getServer();
        if (server != null) {
            return server.getSaveProperties().getGameMode().isSurvivalLike();
        }
        return true;
    }


    @Invoker("damage")
    public abstract boolean invokeDamage(DamageSource source, float amount);

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void injectTick(CallbackInfo info) {
        if (canTakeLineDamage()) {
            //doLineDamage();
        }
    }



}
