package com.github.moistmason.library.world.item.builder;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.function.Supplier;

/**
 * Builds a simple item tier, defined via {@link SimpleTier}. This builder
 * offers a way to make an item tier that's easier to read.
 * @author moist-mason
 */
public class SimpleTierBuilder {
    private TagKey<Block> incorrectBlocksForDrops;
    private int uses;
    private float speed;
    private float attackDamageBonus;
    private int enchantmentValue;
    private Supplier<Ingredient> repairIngredient;

    public SimpleTierBuilder incorrectBlocksForDrops(TagKey<Block> incorrectBlocksForDrops) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        return this;
    }

    public SimpleTierBuilder uses(int uses) {
        this.uses = uses;
        return this;
    }

    public SimpleTierBuilder speed(float speed) {
        this.speed = speed;
        return this;
    }

    public SimpleTierBuilder attackDamageBonus(float attackDamageBonus) {
        this.attackDamageBonus = attackDamageBonus;
        return this;
    }

    public SimpleTierBuilder enchantmentValue(int enchantmentValue) {
        this.enchantmentValue = enchantmentValue;
        return this;
    }

    public SimpleTierBuilder repairIngredient(Supplier<Ingredient> repairIngredient) {
        this.repairIngredient = repairIngredient;
        return this;
    }

    public SimpleTierBuilder repairIngredient(ItemLike item) {
        this.repairIngredient = () -> Ingredient.of(item);
        return this;
    }

    public SimpleTierBuilder repairIngredient(TagKey<Item> tag) {
        this.repairIngredient = () -> Ingredient.of(tag);
        return this;
    }

    public SimpleTier build() {
        return new SimpleTier(incorrectBlocksForDrops, uses, speed, attackDamageBonus, enchantmentValue, repairIngredient);
    }
}
