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

        val validPos = StraightLineManager.getNearestLinePosition(newPlayer.blockPos, newPlayer.getWorld())
        if (validPos != null) {
            newPlayer.requestTeleport(validPos[0].toDouble(), validPos[1].toDouble(), validPos[2].toDouble())
            StraightLineChallenge.LOGGER.info("Teleported to " +
                    "(${validPos[0]}, ${validPos[1]}, ${validPos[2]}) after respawn")
        }

    }
}

class ModPlayerConnectedEvent : PlayerConnectedCallback {
    override fun playerConnected(player: ServerPlayerEntity, server: ServerWorld): ActionResult {

            val iPlayer: IPlayerEntity =  player as IPlayerEntity
            setLine(server)

            val validPos = StraightLineManager.getNearestLinePosition(player.blockPos, server)
            if (validPos != null) {
                player.requestTeleport(validPos[0].toDouble(), validPos[1].toDouble(), validPos[2].toDouble())
                StraightLineChallenge.LOGGER.info("Teleported to " +
                        "(${validPos[0]}, ${validPos[1]}, ${validPos[2]}) after connected")

                return ActionResult.PASS
            }
        return ActionResult.FAIL
    }

    private fun setLine(world: ServerWorld) {
        if (!StraightLineManager.INSTANCE.getIsOverworldLineSet()) {
            val lineX = world.spawnPos.x
            val lineZ = world.spawnPos.z
            StraightLineManager.INSTANCE.setOverworldLine(intArrayOf(lineX, lineZ))
            StraightLineManager.INSTANCE.setIsOverworldLineSet(true)
            StraightLineChallenge.LOGGER.info("Real Line Position set to ($lineX, $lineZ)")
        } else {
            StraightLineChallenge.LOGGER.info("Overworld Line Position was already set to " +
                    "(${StraightLineManager.INSTANCE.getOverworldLine()[0]}, " +
                    "${StraightLineManager.INSTANCE.getOverworldLine()[1]})")
        }

    }
}