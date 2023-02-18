package me.honkling.factory.skript

import ch.njol.skript.Skript
import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.classes.Parser
import ch.njol.skript.expressions.base.EventValueExpression
import ch.njol.skript.lang.ParseContext
import ch.njol.skript.registrations.Classes
import me.honkling.factory.lib.Generator

class ClassInfos {
    companion object {
        init {
            Classes.registerClass(ClassInfo(Generator::class.java, "generator")
                .user("generators?")
                .name("Generator")
                .description("Represents a Factory generator.")
                .examples("generator with id \"basic\"")
                .defaultExpression(EventValueExpression(Generator::class.java))
                .parser(object : Parser<Generator>() {
                    override fun canParse(context: ParseContext?): Boolean {
                        return false
                    }

                    override fun parse(s: String?, context: ParseContext?): Generator? {
                        return null
                    }

                    override fun toString(o: Generator, flags: Int): String {
                        return "${o.name} generator"
                    }

                    override fun toVariableNameString(o: Generator): String {
                        return "generator:${o.blockId}"
                    }
                })
            )
        }
    }
}