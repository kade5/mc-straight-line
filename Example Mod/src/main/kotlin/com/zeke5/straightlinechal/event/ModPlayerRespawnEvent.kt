package com.zeke5.straightlinechal.event

import com.zeke5.straightlinechal.StraightLineChallenge
import com.zeke5.straightlinechal.callbacks.PlayerConnectedCallback
import com.zeke5.straightlinechal.util.IPlayerEntity
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult

class ModPlayerRespawnEvent : ServerPlayerEvents.AfterRespawn{

    override fun afterRespawn(oldPlayer: ServerPlayerEntity?, newPlayer: ServerPlayerEntity?, alive: Boolean) {

        val iPlayer: IPlayerEntity =  newPlayer as IPlayerEntity
        if (newPlayer?.getWorld() is ServerWorld) {
            val world = newPlayer?.getWorld()
            iPlayer.setLineCords(world.spawnPos)
        }

        val validPos = iPlayer.getNearestLinePosition(newPlayer.blockPos)

        if (validPos != null) {
            newPlayer.requestTeleport(validPos[0].toDouble(), validPos[1].toDouble(), validPos[2].toDouble())
            StraightLineChallenge.LOGGER.info("Teleported to (${validPos[0]}, ${validPos[1]}, ${validPos[2]}) after respawn")
        }

    }


}

class ModPlayerConnectedEvent : PlayerConnectedCallback {
    override fun playerConnected(player: ServerPlayerEntity?, server: ServerWorld?): ActionResult {
        val iPlayer: IPlayerEntity =  player as IPlayerEntity
        if (server != null) {
            iPlayer.setLineCords(server.spawnPos)
            val validPos = iPlayer.getNearestLinePosition(player.blockPos)

            if (validPos != null) {
                player.requestTeleport(validPos[0].toDouble(), validPos[1].toDouble(), validPos[2].toDouble())
                StraightLineChallenge.LOGGER.info("Teleported to (${validPos[0]}, ${validPos[1]}, ${validPos[2]}) after connected")
                return ActionResult.PASS
            }
        }
        return ActionResult.FAIL
    }
}