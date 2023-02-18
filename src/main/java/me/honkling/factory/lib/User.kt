package me.honkling.factory.lib

import org.bukkit.Location
import org.bukkit.entity.Player

data class User(
    val player: Player,
    val gens: GeneratorList,
) {
    val taskIds = mutableListOf<Int>()

    fun addGenerator(type: Generator, location: Location) {
        val gen = gens[type] ?: mutableListOf()
        gen.add(location)
        gens[type] = gen
    }

    fun removeGenerator(type: Generator, location: Location) {
        val gen = gens[type] ?: mutableListOf()
        gen.remove(location)
        gens[type] = gen
    }
}
