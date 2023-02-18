package me.honkling.factory.lib

import org.bukkit.plugin.java.JavaPlugin
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class SQL(instance: JavaPlugin) {
	val conn = DriverManager.getConnection("jdbc:sqlite:${instance.dataFolder}/punishments.db")

	init {
		execute("""
			CREATE TABLE IF NOT EXISTS users(
				uuid TEXT NOT NULL PRIMARY KEY,
				gens BLOB NOT NULL
			);
		""".trimIndent())
	}

	fun execute(sql: String, vararg values: Any) {
		prepare(sql, *values).execute()
	}

	fun query(sql: String, vararg values: Any): ResultSet {
		return prepare(sql, *values).executeQuery()
	}

	fun prepare(sql: String, vararg values: Any): PreparedStatement {
		val stmt = conn.prepareStatement(sql)

		values.forEachIndexed { i, v ->
			stmt.setObject(i + 1, v)
		}

		return stmt
	}
}