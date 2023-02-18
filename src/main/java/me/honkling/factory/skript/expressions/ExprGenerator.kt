package me.honkling.factory.skript.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import me.honkling.factory.lib.Generator
import me.honkling.factory.lib.instance
import org.bukkit.event.Event

@Name("Generator")
@Description("Fetches a generator using the specified ID.")
@Examples("generator with id \"basic\"")
@Since("1.0.0")
class ExprGenerator : SimpleExpression<Generator>() {
    companion object {
        init {
            Skript.registerExpression(
                ExprGenerator::class.java,
                Generator::class.java,
                ExpressionType.SIMPLE,
            "generator [(with|by) [id]] %string%")
        }
    }

    lateinit var id: Expression<String>

    override fun init(
        exprs: Array<out Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parseResult: SkriptParser.ParseResult?
    ): Boolean {
        id = exprs[0] as Expression<String>

        return true
    }

    override fun get(e: Event?): Array<Generator>? {
        val identifier = id.getSingle(e)

        if (!instance.config.gens.containsKey(identifier)) {
            Skript.error("Tried to get generator using an invalid ID.")
            return null
        }

        return arrayOf(instance.config.gens[identifier]!!)
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun getReturnType(): Class<out Generator> {
        return Generator::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "generator with id '$id'"
    }
}
