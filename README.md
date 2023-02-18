# Factory

Factory is a flexible, customizable, lightweight generator core.<br>
It is designed to act only as a base, and then to be extended using your own plugins.

# Configuration

You can configure the types of generators in the `gens.toml` file.
```toml
[gens.basic]
name = "&eBasic" # The prefix for the gen blocks and items.
blockId = "minecraft:yellow_glazed_terracotta" # Block used for the gen
itemId = "minecraft:yellow_dye" # Item dropped by the gen
cooldown = 200 # How often (in ticks) an item will be dropped by a gen
```

# Skript Integration

Factory provides a few simple expressions for accessing generators.

## Generator

Fetches a generator using the specified ID.
```
generator [(with|by) [id]] %string%
```

## Generator Item

Returns the item for a generator.
```
item of %generator%
```

## Generator Produce

Returns the produce for a generator.
```
produce of %generator%
```
