package me.honkling.factory.listeners

import me.honkling.factory.lib.getUser
import me.honkling.factory.lib.instance
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

object BlockModifyListener : Listener {
    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        val player = e.player
        val item = e.itemInHand
        val generator = instance.config.gens.values.find { it.getBlock() == item.asOne() }
            ?: return

        player.getUser().addGenerator(generator, e.block.location)
    }

    @EventHandler
    fun onBlockBreak(e: PlayerInteractEvent) {
        if (e.action != Action.LEFT_CLICK_BLOCK)
            return

        val player = e.player
        val user = player.getUser()
        val block = e.clickedBlock ?: return
        val generator = instance.config.gens.values.find { it.block == block.type }
            ?: return

        if (!user.gens.containsKey(generator) || !user.gens[generator]!!.contains(block.location))
            return

        user.removeGenerator(generator, block.location)
        block.type = Material.AIR
        player.inventory.addItem(generator.getBlock())
    }
}