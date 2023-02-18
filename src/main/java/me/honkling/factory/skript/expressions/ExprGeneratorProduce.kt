package me.honkling.factory.skript.expressions

import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.expressions.base.SimplePropertyExpression
import me.honkling.factory.lib.Generator
import org.bukkit.inventory.ItemStack

@Name("Generator Produce")
@Description("Returns the produce for a generator.")
@Examples("produce of generator with id \"basic\"")
@Since("1.0.0")
class ExprGeneratorProduce : SimplePropertyExpression<Generator, ItemStack>() {
    companion object {
        init {
            register(
                ExprGeneratorProduce::class.java,
                ItemStack::class.java,
                "produce",
                "generator"
            )
        }
    }

    override fun convert(generator: Generator): ItemStack? {
        return generator.getProduce()
    }

    override fun getReturnType(): Class<out ItemStack> {
        return ItemStack::class.java
    }

    override fun getPropertyName(): String {
        return "generator produce"
    }
}