package com.github.moistmason.library.registry;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

import static com.github.moistmason.library.registry.factory.BlockFactories.*;

/**
 * Supplier helper methods for block registration.
 * @author moist-mason
 */
public class BlockRegistrySuppliers {

    /** Creates a simple block with its properties copied from a parent block. */
    public static Supplier<Block> supply(Block parent) {
        return supply(Block::new, parent);
    }

    /** Creates a simple block with its own defined properties. */
    public static Supplier<Block> supply(Properties properties) {
        return supply(Block::new, properties);
    }

    /** Creates a simple block with its properties copied from a parent block and its own unique map color. */
    public static Supplier<Block> supply(Block parent, MapColor color) {
        return supply(Block::new, parent.properties(), color);
    }

    /** Creates a simple block with its own defined properties and map color. */
    public static Supplier<Block> supply(Properties properties, MapColor color) {
        return supply(Block::new, properties, color);
    }

    /** Creates a block with its properties copied from its parent. */
    public static <T extends Block> Supplier<T> supply(BlockFactory<T> factory, Block parent) {
        return () -> factory.create(parent.properties());
    }

    /** Creaets a block with its properties copied from a parent block and its own unique map color. */
    public static <T extends Block> Supplier<T> supply(BlockFactory<T> factory, Block parent, MapColor color) {
        return () -> factory.create(parent.properties().mapColor(color));
    }

    /** Creates a block with its own defined properties. */
    public static <T extends Block> Supplier<T> supply(BlockFactory<T> factory, Properties properties) {
        return () -> factory.create(properties);
    }

    /** Creates a block with its own defined properties and map color. */
    public static <T extends Block> Supplier<T> supply(BlockFactory<T> factory, Properties properties, MapColor color) {
        return () -> factory.create(properties.mapColor(color));
    }

    /** Creates an XP-dropping block with its properties copied from a parent block. */
    public static <T extends DropExperienceBlock> Supplier<T> supplyExperienceBlock(DropExperienceBlockFactory<T> factory, int min, int max, Block parent) {
        return () -> factory.create(UniformInt.of(min, max), parent.properties());
    }

    /** Creates an XP-dropping block with its properties copied from a parent block and its own unique map color. */
    public static <T extends DropExperienceBlock> Supplier<T> supplyExperienceBlock(DropExperienceBlockFactory<T> factory, int min, int max, Block parent, MapColor color) {
        return () -> factory.create(UniformInt.of(min, max), parent.properties().mapColor(color));
    }

    /** Creates an XP-dropping block with its own defined properties. */
    public static <T extends DropExperienceBlock> Supplier<T> supplyExperienceBlock(DropExperienceBlockFactory<T> factory, int min, int max, Properties properties) {
        return () -> factory.create(UniformInt.of(min, max), properties);
    }

    /** Creates an XP-dropping block with its own defined properties and map color. */
    public static <T extends DropExperienceBlock> Supplier<T> supplyExperienceBlock(DropExperienceBlockFactory<T> factory, int min, int max, Properties properties, MapColor color) {
        return () -> factory.create(UniformInt.of(min, max), properties.mapColor(color));
    }

    /** Creates a stair block with its properties copied from its parent. */
    public static <T extends StairBlock> Supplier<T> supplyStairs(StairBlockFactory<T> factory, DeferredBlock<? extends Block> base) {
        return () -> factory.create(base.get().defaultBlockState(), base.get().properties());
    }

    /** Creates a stair block with its own unique properties. */
    public static <T extends StairBlock> Supplier<T> supplyStairs(StairBlockFactory<T> factory, DeferredBlock<? extends Block> base, Properties properties) {
        return () -> factory.create(base.get().defaultBlockState(), properties);
    }

    /** Creates a stair block with its own unique properties and a defined map color. */
    public static <T extends StairBlock> Supplier<T> supplyStairs(StairBlockFactory<T> factory, DeferredBlock<? extends Block> base, Properties properties, MapColor color) {
        return () -> factory.create(base.get().defaultBlockState(), properties.mapColor(color));
    }
}
