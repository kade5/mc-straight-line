package com.zeke5.straightlinechal.mixin;

import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(World.class)
public interface WorldMixin {

    @Accessor
    MutableWorldProperties getProperties();

}

