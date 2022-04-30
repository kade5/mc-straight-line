package com.zeke5.straightlinechal
import com.zeke5.straightlinechal.block.LineRefractorBlock
import com.zeke5.straightlinechal.callbacks.PlayerChangeDimensionCallback
import com.zeke5.straightlinechal.callbacks.PlayerConnectedCallback
import com.zeke5.straightlinechal.event.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("UNUSED")
object StraightLineChallenge: ModInitializer {



    const val MOD_ID = "straightlinechal"
    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        registerEvents()
        registerBlocks()
        LOGGER.info("$MOD_ID has been initialized.")
    }

    private fun registerEvents() {
        ServerWorldEvents.LOAD.register(ModServerLoadEvent())
        ServerPlayerEvents.AFTER_RESPAWN.register(ModPlayerRespawnEvent())
        PlayerConnectedCallback.EVENT.register(ModPlayerConnectedEvent())
        PlayerChangeDimensionCallback.EVENT.register(ModPlayerChangeDimensionEvent())
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(ModPlayerChangedWorldEvent())
    }

    private fun registerBlock(name: String, block: Block, group: ItemGroup) : Block {
        registerBlockItem(name, block, group)
        return Registry.register(Registry.BLOCK, Identifier(MOD_ID, name), block)
    }

    private fun registerBlockItem(name: String, block: Block, group: ItemGroup): Item {
        return Registry.register(Registry.ITEM, Identifier(MOD_ID, name), BlockItem(block,
            FabricItemSettings().group(group)))
    }

    private fun registerBlocks() {
        val LINE_REFRACTOR = registerBlock("line_refractor", LineRefractorBlock(FabricBlockSettings.copy(Blocks.GLOWSTONE)), ItemGroup.MISC)
    }
}