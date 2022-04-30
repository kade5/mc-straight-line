package com.zeke5.straightlinechal.util

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.MutableWorldProperties

class ModUtils {
    companion object Factory {
        val NETHER_SIZE = 8
        val outOfLineDamage = 0.5f

        fun convertToNetherCoords(coords: IntArray): IntArray {
            return intArrayOf(coords[0] / NETHER_SIZE, coords[1] / NETHER_SIZE)
        }


        fun getSpawnFromProperties(properties: MutableWorldProperties): IntArray {
            return intArrayOf(properties.spawnX, properties.spawnY, properties.spawnZ)
        }
    }
}