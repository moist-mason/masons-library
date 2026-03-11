package com.github.moistmason.library.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import static com.github.moistmason.library.registry.factory.BlockFactories.*;

import java.util.function.Supplier;

/**
 * Supplier helper methods for block registration.
 * @author moist-mason
 */
public class BlockRegistrySuppliers {

    /**
     * Creates a simple block with its parent properties copied in full.
     */
    public static Supplier<Block> supply(Block parent) {
        return supply(Block::new, parent.properties());
    }

    /**
     * Creates a simple block with its own defined properties.
     */
    public static Supplier<Block> supply(Properties properties) {
        return supply(Block::new, properties);
    }

    /**
     * Creates a simple block with its properties copied from a parent block and its own unique map color.
     */
    public static Supplier<Block> supply(Block parent, MapColor color) {
        return supply(Block::new, parent.properties(), color);
    }

    /**
     * Creates a simple block with its own defined properties and map color.
     */
    public static Supplier<Block> supply(Properties properties, MapColor color) {
        return supply(Block::new, properties, color);
    }

    /**
     * Creates a block.
     */
    public static <T extends Block> Supplier<T> supply(BlockFactory<T> factory, Properties properties) {
        return () -> factory.create(properties);
    }

    /**
     * Creates a block with a defined map color.
     */
    public static <T extends Block> Supplier<T> supply(BlockFactory<T> factory, Properties properties, MapColor color) {
        return () -> factory.create(properties.mapColor(color));
    }

    /**
     * Creates a stair block with its properties copied from its parent.
     */
    public static <T extends StairBlock> Supplier<T> supplyStairs(StairBlockFactory<T> factory, DeferredBlock<? extends Block> base) {
        return () -> factory.create(base.get().defaultBlockState(), base.get().properties());
    }

    /**
     * Creates a stair block with its own unique properties.
     */
    public static <T extends StairBlock> Supplier<T> supplyStairs(StairBlockFactory<T> factory, DeferredBlock<? extends Block> base, Properties properties) {
        return () -> factory.create(base.get().defaultBlockState(), properties);
    }

    /**
     * Creates a stair block with its own unique properties and a defined map color.
     */
    public static <T extends StairBlock> Supplier<T> supplyStairs(StairBlockFactory<T> factory, DeferredBlock<? extends Block> base, Properties properties, MapColor color) {
        return () -> factory.create(base.get().defaultBlockState(), properties.mapColor(color));
    }
}
