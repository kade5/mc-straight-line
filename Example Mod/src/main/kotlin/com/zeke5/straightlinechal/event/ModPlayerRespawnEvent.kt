package com.zeke5.straightlinechal.event

import com.zeke5.straightlinechal.StraightLineChallenge
import com.zeke5.straightlinechal.callbacks.PlayerConnectedCallback
import com.zeke5.straightlinechal.manager.StraightLineManager
import com.zeke5.straightlinechal.mixin.WorldMixin
import com.zeke5.straightlinechal.util.IPlayerEntity
import com.zeke5.straightlinechal.util.ModUtils
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos

class ModPlayerRespawnEvent : ServerPlayerEvents.AfterRespawn{

    override fun afterRespawn(oldPlayer: ServerPlayerEntity?, newPlayer: ServerPlayerEntity?, alive: Boolean) {

        val iPlayer: IPlayerEntity =  newPlayer as IPlayerEntity

        val validPos = iPlayer.getNearestLinePosition(newPlayer.blockPos)
        if (validPos != null) {
            newPlayer.requestTeleport(validPos[0].toDouble(), validPos[1].toDouble(), validPos[2].toDouble())
            StraightLineChallenge.LOGGER.info("Teleported to (${validPos[0]}, ${validPos[1]}, ${validPos[2]}) after respawn")
        }

    }
}

class ModPlayerConnectedEvent : PlayerConnectedCallback {
    override fun playerConnected(player: ServerPlayerEntity, server: ServerWorld): ActionResult {

            val iPlayer: IPlayerEntity =  player as IPlayerEntity

            val validPos = iPlayer.getNearestLinePosition(player.blockPos)
            if (validPos != null) {
                player.requestTeleport(validPos[0].toDouble(), validPos[1].toDouble(), validPos[2].toDouble())
                StraightLineChallenge.LOGGER.info("Teleported to (${validPos[0]}, ${validPos[1]}, ${validPos[2]}) after connected")
                StraightLineChallenge.LOGGER.info("Straight Line position is ${iPlayer.getLineX()}, ${iPlayer.getLineZ()}")
                setLine(server)
                return ActionResult.PASS
            }
        return ActionResult.FAIL
    }

    fun setLine(world: ServerWorld) {
        if (!StraightLineManager.INSTANCE.getIsOverworldLineSet()) {
            val lineX = world.spawnPos.x
            val lineZ = world.spawnPos.z
            StraightLineManager.INSTANCE.setOverworldLine(intArrayOf(lineX, lineZ))
            StraightLineManager.INSTANCE.setIsOverworldLineSet(true)
            StraightLineChallenge.LOGGER.info("Real Line Position set to ($lineX, $lineZ)")
        } else {
            StraightLineChallenge.LOGGER.info("Real Line Position was already loaded")
        }

    }
}