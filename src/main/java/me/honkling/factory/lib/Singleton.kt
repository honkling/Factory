package me.honkling.factory.lib

import me.honkling.factory.Factory
import org.bukkit.Bukkit

val instance = Bukkit.getPluginManager().getPlugin("Factory")!! as Factory