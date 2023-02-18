package me.honkling.factory.lib

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class Generator(
    val name: String,
    val blockId: String,
    val itemId: String,
    val cooldown: Long,
    val block: Material = Material.matchMaterial(blockId)!!,
    val item: Material = Material.matchMaterial(itemId)!!
) {
    fun getProduce(): ItemStack {
        val item = ItemStack(item)

        val meta = item.itemMeta

        meta.displayName(name
            .toComponent()
            .append(Component
                .text(" Item"))
            .decoration(TextDecoration.ITALIC, false))

        item.itemMeta = meta

        return item
    }

    fun getBlock(): ItemStack {
        val item = ItemStack(block)

        val meta = item.itemMeta

        meta.displayName(name
            .toComponent()
            .append(Component
                .text(" Generator"))
            .decoration(TextDecoration.ITALIC, false))

        item.itemMeta = meta

        return item
    }
}
