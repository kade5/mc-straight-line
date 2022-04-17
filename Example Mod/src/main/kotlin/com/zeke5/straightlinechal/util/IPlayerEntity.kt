package com.zeke5.straightlinechal.util

import net.minecraft.util.math.BlockPos

interface IPlayerEntity {
    fun getNearestLinePosition(blockPos: BlockPos): IntArray?
    fun getLineX(): Int
    fun getLineY(): Int
    fun getLineZ(): Int
}