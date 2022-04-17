package com.zeke5.straightlinechal.event

import com.zeke5.straightlinechal.StraightLineChallenge
import com.zeke5.straightlinechal.manager.StraightLineManager
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld

class ModServerLoadEvent : ServerWorldEvents.Load {
    override fun onWorldLoad(server: MinecraftServer, world: ServerWorld) {
        if (world == server.overworld) {

            StraightLineManager.INSTANCE = (world.persistentStateManager.getOrCreate({nbt: NbtCompound ->
                StraightLineManager.readNbt(nbt)}, {StraightLineManager()}, "straight_line_challenge")) as StraightLineManager

            setLine(world)
        }
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