package com.zeke5.straightlinechal.manager

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.world.PersistentState
import net.minecraft.world.World
import org.apache.http.config.Registry

class StraightLineManager : PersistentState() {

    companion object Manage {
        var INSTANCE = StraightLineManager()
        val SOURCELINEID = 0

        fun readNbt(nbt: NbtCompound): PersistentState {
            val manager = StraightLineManager()
            manager.overworldLine = nbt.getIntArray("overworld_line")
            manager.netherLine = nbt.getIntArray("nether_line")
            manager.isOverworldLineSet = nbt.getBoolean("is_overworld_line_set")
            createBlockLineLists(manager, nbt)
            INSTANCE = manager

            return manager
        }

        private fun createBlockLineLists(manager: StraightLineManager, nbt: NbtCompound) {
            val list = nbt.getList("straightlinechal.block_lines", NbtElement.COMPOUND_TYPE as Int)
            for (element in list) {
                val blockNbt = element as NbtCompound
                val blockPos = BlockPos(blockNbt.getInt("blockX"), blockNbt.getInt("blockY"),
                    blockNbt.getInt("blockZ"))
                manager.addLineBlock(blockPos, blockNbt.getString("world_type"))
            }
        }

        fun getNearestLinePosition(blockPos: BlockPos, server: ServerWorld): IntArray {
            var posX = blockPos.x
            var posY = blockPos.y
            var posZ = blockPos.z
            val lineX : Int
            val lineZ: Int
            if (server.registryKey == World.NETHER) {
                lineX = INSTANCE.netherLine[0]
                lineZ = INSTANCE.netherLine[1]
            } else {
                lineX = INSTANCE.overworldLine[0]
                lineZ = INSTANCE.overworldLine[1]
            }
            if (Math.abs(posX - lineX) <= Math.abs(posZ - lineZ)) {
                posX = lineX
            } else {
                posZ = lineZ
            }
            return intArrayOf(posX, posY, posZ)
        }
    }

    private var overworldLine = intArrayOf(0, 0)
    private var netherLine = intArrayOf(-1, -1)
    private var isOverworldLineSet = false
    private var overworldBlockLines = HashMap<Int, BlockPos>()
    private var overworldXLines = HashMap<Int, MutableList<Int>>()
    private var overworldZLines = HashMap<Int, MutableList<Int>>()
    private var nextOverworldKey = 1

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        nbt.putIntArray("straightlinechal.overworld_line", this.overworldLine)
        nbt.putIntArray("straightlinechal.nether_line", this.netherLine)
        nbt.putBoolean("straightlinechal.is_overworld_line_set", this.isOverworldLineSet)

        val nbtList = NbtList()

        for ((key, pos) in this.overworldBlockLines) {
            nbtList.add(addLineNbt(pos, "overworld"))
        }
        nbt.put("straightlinechal.overworld_block_lines", nbtList)

        return nbt
    }

    fun addLineBlock(blockPos: BlockPos, worldType: String) {
        if (worldType == "overworld") {
            this.overworldBlockLines[this.nextOverworldKey] = blockPos
            this.addLineBlockToLists(blockPos, worldType, this.nextOverworldKey)
            this.nextOverworldKey++
        }

    }

    fun addLineBlockToLists(blockPos: BlockPos, worldType: String, key: Int) {
        if (worldType == "overworld") {
            if (this.overworldXLines.containsKey(blockPos.x)) {
                this.overworldXLines[blockPos.x]?.add(key)
            } else {
                this.overworldXLines[blockPos.x] = mutableListOf(key)
            }
            if (this.overworldZLines.containsKey(blockPos.z)) {
                this.overworldZLines[blockPos.z]?.add(key)
            } else {
                this.overworldZLines[blockPos.z] = mutableListOf(key)
            }
        }

    }

    fun addLineNbt(pos: BlockPos, worldType: String): NbtCompound {
        val nbt = NbtCompound()
        nbt.putInt("blockX", pos.x)
        nbt.putInt("blockY", pos.y)
        nbt.putInt("blockZ", pos.z)
        nbt.putString("world_type", worldType)

        return nbt
    }

    fun setOverworldLine(line: IntArray) {
        this.overworldLine = line
        this.markDirty()
    }

    fun setNetherLine(line: IntArray) {
        this.netherLine = line
        this.markDirty()
    }

    fun setIsOverworldLineSet(boolean: Boolean) {
        this.isOverworldLineSet = boolean
        this.markDirty()
    }

    fun resetNetherLine() {
        this.netherLine = intArrayOf(-1, -1)
        this.markDirty()
    }

    fun getOverworldLine() : IntArray {
        return this.overworldLine
    }

    fun getNetherLine() : IntArray {
        return this.netherLine
    }

    fun getIsOverworldLineSet() : Boolean {
        return this.isOverworldLineSet
    }


}