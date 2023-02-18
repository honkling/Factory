package me.honkling.factory

import cc.ekblad.toml.decode
import cc.ekblad.toml.tomlMapper
import ch.njol.skript.Skript
import me.honkling.factory.lib.Configuration
import me.honkling.factory.lib.Generator
import me.honkling.factory.lib.SQL
import me.honkling.factory.listeners.BlockModifyListener
import me.honkling.factory.listeners.PlayerLifecycleListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Factory : JavaPlugin() {
    val config: Configuration
    val sql: SQL

    init {
        dataFolder.mkdir()
        sql = SQL(this)

        val configFile = dataFolder.resolve("gens.toml")
        saveResource("gens.toml", false)

        val mapper = tomlMapper {}

        config = mapper.decode(configFile.toPath())
    }

    override fun onEnable() {
        val pluginManager = Bukkit.getPluginManager()

        pluginManager.registerEvents(BlockModifyListener, this)
        pluginManager.registerEvents(PlayerLifecycleListener, this)

        if (!pluginManager.isPluginEnabled("Skript")) return

        logger.info("Detected Skript! Registering syntax.")

        val addon = Skript.registerAddon(this)
        addon.loadClasses("me.honkling.factory", "skript")
    }

    override fun onDisable() {
        sql.conn.close()
    }
}