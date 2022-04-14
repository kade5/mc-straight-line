package com.zeke5.straightlinechal.util

import net.minecraft.util.math.BlockPos

interface IPlayerEntity {
    fun setLineCords(blockPos: BlockPos)
    fun getNearestLinePosition(blockPos: BlockPos): IntArray?
}