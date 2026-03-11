package com.github.moistmason.library.registry.factory;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Block factory interfaces.
 * @author moist-mason
 */
public class BlockFactories {

    /** Standard block factory. */
    @FunctionalInterface
    public interface BlockFactory<T extends Block> {
        T create(Properties properties);
    }

    /** Stair block factory. */
    @FunctionalInterface
    public interface StairBlockFactory<T extends StairBlock> {
        T create(BlockState parentState, Properties properties);
    }
}
