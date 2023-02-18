package me.honkling.factory.lib

import me.honkling.factory.Factory
import org.bukkit.Bukkit
import org.bukkit.Location
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.InputStream
import java.sql.Blob

// (generator id, string)
// (count, 4 bytes, int)
// (world, string)
// (x, 8 bytes, float64)
// (y, 8 bytes, float64)
// (z, 8 bytes, float64)
fun serializeGenerators(gens: GeneratorList): ByteArray {
    val byteStream = ByteArrayOutputStream()
    val dataStream = DataOutputStream(byteStream)

    val indices = instance.config.gens.entries.associateBy({ it.value }) { it.key }

    gens.forEach { gen ->
        val locations = gen.value

        dataStream.writeUTF(indices[gen.key]!!)
        dataStream.writeInt(locations.size)

        locations.forEach { dataStream.writeLocation(it) }
    }

    return byteStream.toByteArray()
}

fun deserializeGenerators(stream: InputStream): GeneratorList {
    val gens = GeneratorList()
    val dataStream = DataInputStream(stream)

    while (true) {
        try {
            val id = dataStream.readUTF()
            val count = dataStream.readInt()
            val locations = mutableListOf<Location>()

            var i = 0
            while (i < count) {
                locations.add(dataStream.readLocation())
                i++
            }

            gens[instance.config.gens[id]!!] = locations
        } catch (e: EOFException) {
            break
        }
    }

    stream.close()

    return gens
}

