package me.honkling.factory.listeners

import me.honkling.factory.lib.getUser
import me.honkling.factory.lib.instance
import me.honkling.factory.lib.serializeGenerators
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

object PlayerLifecycleListener : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        val user = player.getUser()

        instance.config.gens.forEach { (_, gen) ->
            val task = Bukkit.getScheduler().runTaskTimer(instance, Runnable {
                val locations = user.gens[gen] ?: return@Runnable

                if (locations.isEmpty())
                    return@Runnable

                val spawnLocation = locations.last().clone().add(0.0, 1.0, 0.0)

                if (!spawnLocation.isChunkLoaded)
                    return@Runnable

                val item = spawnLocation.world.dropItem(spawnLocation, gen.getProduce().asQuantity(locations.size))
                item.velocity = Vector(0, 0, 0)
                item.teleport(spawnLocation)
            }, gen.cooldown, gen.cooldown)

            user.taskIds.add(task.taskId)
        }
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        val player = e.player
        val user = player.getUser()
        val scheduler = Bukkit.getScheduler()

        user.taskIds.forEach { scheduler.cancelTask(it) }
        user.taskIds.clear()

        instance.sql.execute(
            "INSERT OR REPLACE INTO users(uuid, gens) VALUES(?, ?);",
            player.uniqueId,
            serializeGenerators(user.gens)
        )
    }
}