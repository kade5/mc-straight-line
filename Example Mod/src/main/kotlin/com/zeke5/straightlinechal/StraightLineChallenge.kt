package com.zeke5.straightlinechal
import com.zeke5.straightlinechal.callbacks.PlayerChangeDimensionCallback
import com.zeke5.straightlinechal.callbacks.PlayerConnectedCallback
import com.zeke5.straightlinechal.event.*
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("UNUSED")
object StraightLineChallenge: ModInitializer {



    const val MOD_ID = "straightlinechal"
    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        LOGGER.info("$MOD_ID has been initialized.")
        registerEvents()
    }

    private fun registerEvents() {
        ServerWorldEvents.LOAD.register(ModServerLoadEvent())
        ServerPlayerEvents.AFTER_RESPAWN.register(ModPlayerRespawnEvent())
        PlayerConnectedCallback.EVENT.register(ModPlayerConnectedEvent())
        PlayerChangeDimensionCallback.EVENT.register(ModPlayerChangeDimensionEvent())
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(ModPlayerChangedWorldEvent())
    }
}