package me.honkling.factory.lib

import me.honkling.factory.Factory
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.nio.ByteBuffer
import java.util.*

private val userCache = mutableMapOf<UUID, User>()

fun Player.getUser(): User {
    if (!userCache.containsKey(uniqueId)) {
        val rs = instance.sql.query("SELECT gens FROM users WHERE uuid = ?", uniqueId)

        if (!rs.next()) {
            val user = User(this, GeneratorList())
            userCache[uniqueId] = user
            return user
        }

        val gens = deserializeGenerators(rs.getBinaryStream("gens"))

        val user = User(this, gens)
        userCache[uniqueId] = user
        return user
    }

    return userCache[uniqueId]!!
}

fun String.toComponent(): TextComponent {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(this)
}

fun DataOutputStream.writeLocation(location: Location) {
    writeUTF(location.world.name)
    writeDouble(location.x)
    writeDouble(location.y)
    writeDouble(location.z)
}

fun DataInputStream.readLocation(): Location {
    val world = readUTF()
    val x = readDouble()
    val y = readDouble()
    val z = readDouble()

    return Location(Bukkit.getWorld(world)!!, x, y, z)
}