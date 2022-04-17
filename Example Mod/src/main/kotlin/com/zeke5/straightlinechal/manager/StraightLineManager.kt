package com.zeke5.straightlinechal.manager

import net.minecraft.nbt.NbtCompound
import net.minecraft.world.PersistentState

class StraightLineManager : PersistentState() {

    companion object Manage {
        var INSTANCE = StraightLineManager()

        fun readNbt(nbt: NbtCompound): PersistentState {
            val manager = StraightLineManager()
            manager.overworldLine = nbt.getIntArray("overworld_line")
            manager.netherLine = nbt.getIntArray("nether_line")
            manager.isOverworldLineSet = nbt.getBoolean("is_overworld_line_set")
            INSTANCE = manager

            return manager
        }
    }

    private var overworldLine = intArrayOf(0, 0)
    private var netherLine = intArrayOf(-1, -1)
    private var isOverworldLineSet = false

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        nbt.putIntArray("overworld_line", this.overworldLine)
        nbt.putIntArray("nether_line", this.netherLine)
        nbt.putBoolean("is_overworld_line_set", this.isOverworldLineSet)

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