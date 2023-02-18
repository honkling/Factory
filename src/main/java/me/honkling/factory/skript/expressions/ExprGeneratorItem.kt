package me.honkling.factory.skript.expressions

import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import me.honkling.factory.lib.Generator
import org.bukkit.inventory.ItemStack

@Name("Generator Item")
@Description("Returns the item for a generator.")
@Examples("item of generator with id \"basic\"")
@Since("1.0.0")
class ExprGeneratorItem : SimplePropertyExpression<Generator, ItemStack>() {
    companion object {
        init {
            register(
                ExprGeneratorItem::class.java,
                ItemStack::class.java,
                "item",
                "generator"
            )
        }
    }

    override fun convert(generator: Generator): ItemStack? {
        return generator.getBlock()
    }

    override fun getReturnType(): Class<out ItemStack> {
        return ItemStack::class.java
    }

    override fun getPropertyName(): String {
        return "generator item"
    }
}