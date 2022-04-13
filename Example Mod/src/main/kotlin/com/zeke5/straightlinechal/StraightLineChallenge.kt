package com.zeke5.straightlinechal
import com.zeke5.straightlinechal.event.ModPlayerRespawnEvent
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
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
        val respawnEvent = ModPlayerRespawnEvent()
        ServerPlayerEvents.AFTER_RESPAWN.register(respawnEvent)
    }
}