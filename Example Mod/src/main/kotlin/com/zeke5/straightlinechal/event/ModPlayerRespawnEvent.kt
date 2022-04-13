package com.zeke5.straightlinechal.event

import com.zeke5.straightlinechal.util.IPlayerEntity
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld

class ModPlayerRespawnEvent : ServerPlayerEvents.AfterRespawn{

    override fun afterRespawn(oldPlayer: ServerPlayerEntity?, newPlayer: ServerPlayerEntity?, alive: Boolean) {

        val iPlayer: IPlayerEntity =  newPlayer as IPlayerEntity
        if (newPlayer?.getWorld() is ServerWorld) {
            val world = newPlayer?.getWorld()
            iPlayer.setLineCords(world.spawnPos)
        }



    }
}