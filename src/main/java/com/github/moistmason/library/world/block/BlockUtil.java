package com.github.moistmason.library.world.block;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;

public final class BlockUtil {
    public static boolean containsTag(Block block, TagKey<Block> tag) {
        return block.defaultBlockState().getTags().anyMatch(tag::equals);
    }

    public static boolean containsTag(Block block, List<TagKey<Block>> tags) {
        return block.defaultBlockState().getTags().anyMatch(tags::contains);
    }

    public static boolean containsAnyTag(Block block) {
        return !block.defaultBlockState().getTags().toList().isEmpty();
    }
}
