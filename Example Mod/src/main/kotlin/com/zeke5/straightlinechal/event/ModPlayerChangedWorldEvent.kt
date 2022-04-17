package com.zeke5.straightlinechal.event

import com.zeke5.straightlinechal.StraightLineChallenge
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents
import net.minecraft.block.Blocks
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.util.registry.Registry.BLOCK
import net.minecraft.world.World

class ModPlayerChangedWorldEvent : ServerEntityWorldChangeEvents.AfterPlayerChange {
    override fun afterChangeWorld(player: ServerPlayerEntity?, origin: ServerWorld?, destination: ServerWorld?) {
        if (destination?.registryKey == World.NETHER) {
            val blockState = player?.getWorld()?.getBlockState(player?.blockPos)
            StraightLineChallenge.LOGGER.info(blockState?.block?.translationKey)
            StraightLineChallenge.LOGGER.info("Block is at (${player?.blockPos?.x}, ${player?.blockPos?.y}," +
                    " ${player?.blockPos?.z})")
            if (blockState != null && blockState.block == Blocks.NETHER_PORTAL) {
                StraightLineChallenge.LOGGER.info(blockState.get(Properties.HORIZONTAL_AXIS).getName())

            }
        }
    }
}