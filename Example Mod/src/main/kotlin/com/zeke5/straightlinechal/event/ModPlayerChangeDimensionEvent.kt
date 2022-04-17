package com.zeke5.straightlinechal.event

import com.zeke5.straightlinechal.StraightLineChallenge
import com.zeke5.straightlinechal.callbacks.PlayerChangeDimensionCallback
import com.zeke5.straightlinechal.util.IPlayerEntity
import com.zeke5.straightlinechal.util.ModUtils
import net.minecraft.block.BlockState
import net.minecraft.block.NetherPortalBlock
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.world.World

class ModPlayerChangeDimensionEvent : PlayerChangeDimensionCallback{
    override fun playerChangeDimension(player: ServerPlayerEntity, server: ServerWorld): ActionResult {
        /*if (player != null && server != null) {
            var spawnPos = intArrayOf(server.spawnPos.x, server.spawnPos.y)
            val iPlayer = player as IPlayerEntity
            if (server.registryKey == World.OVERWORLD) {
                iPlayer.setLineCords(spawnPos)
                return ActionResult.PASS
            } else if (server.registryKey == World.NETHER) {
                spawnPos = ModUtils.convertToNetherCoords(spawnPos)
                iPlayer.setLineCords(spawnPos)
                return ActionResult.PASS
            }
        }
        val iPlayer = player as IPlayerEntity
        if (server?.registryKey == World.NETHER) {
            val linePosition = ModUtils.convertToNetherCoords(intArrayOf(iPlayer.getLineX(), iPlayer.getLineZ()))
            StraightLineChallenge.LOGGER.info("Nether line position set to (${linePosition[0]}, ${linePosition[1]})")
        }

        val blockState = player.getWorld().getBlockState(player.blockPos)
        StraightLineChallenge.LOGGER.info(blockState.block.translationKey)
        StraightLineChallenge.LOGGER.info("Block is at (${player.blockPos.x}, ${player.blockPos.y}," +
                " ${player.blockPos.z})")
        //StraightLineChallenge.LOGGER.info("The nether portal is facing ${blockState.get(Properties.HORIZONTAL_FACING).getName()}")*/
        return ActionResult.FAIL
    }


}
