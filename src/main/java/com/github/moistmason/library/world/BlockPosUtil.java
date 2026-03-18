package com.github.moistmason.library.world;

import net.minecraft.core.BlockBox;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.rmi.dgc.Lease;
import java.util.*;

/**
 * <p> Utility methods related to block positions and block boxes. </p>
 * <p> See: {@link BlockPos} <br>
 *     See: {@link BlockBox} </p>
 * @author moist-mason
 */
public final class BlockPosUtil {

    /**
     * Inflates a block box on all sides by the defined amounts.
     * @param box The starting box.
     * @param xOffset how much the box expands along the x-axis (in both directions).
     * @param yOffset how much the box expands along the y-axis (in both directions).
     * @param zOffset how much the box expands along the z-axis (in both directions).
     * @return The inflated box.
     * @see net.minecraft.world.phys.AABB#inflate(double, double, double)
     */
    public static BlockBox expandBox(BlockBox box, int xOffset, int yOffset, int zOffset) {
        BlockPos min = new BlockPos(
                box.min().getX() - xOffset,
                box.min().getY() - yOffset,
                box.min().getZ() - zOffset
        );

        BlockPos max = new BlockPos(
                box.max().getX() + xOffset,
                box.max().getY() + yOffset,
                box.max().getZ() + zOffset
        );

        return BlockBox.of(min, max);
    }

    /**
     * Shrinks a block box on all sides by the defined amounts.
     * @param box The starting box.
     * @param xOffset how much the box contracts along the x-axis (in both directions).
     * @param yOffset how much the box contracts along the y-axis (in both directions).
     * @param zOffset how much the box contracts along the z-axis (in both directions).
     * @return The inflated box.
     * @see net.minecraft.world.phys.AABB#contract(double, double, double)
     */
    public static BlockBox contractBox(BlockBox box, int xOffset, int yOffset, int zOffset) {
        BlockPos min = new BlockPos(
                box.min().getX() + xOffset,
                box.min().getY() + yOffset,
                box.min().getZ() + zOffset
        );

        BlockPos max = new BlockPos(
                box.max().getX() - xOffset,
                box.max().getY() - yOffset,
                box.max().getZ() - zOffset
        );

        return BlockBox.of(min, max);
    }

    /**
     * Expands a box along the x- and z-axes, while vertically extending either from above or below the starting position.
     * @param box The starting box.
     * @param xOffset How much the box extends along the x-axis (in both directions) from the starting position.
     * @param zOffset How much the box extends along the z-axis (in both directions) from the starting position.
     * @param height The absolute value of this number defines the height of the box. A positive value means the box will extend
     *                <i>above</i> the starting position, while negative values mean the box will extend <i>below</i> the starting position.
     * @return The expanded box.
     * @see net.minecraft.world.phys.AABB#expandTowards(double, double, double)
     */
    public static BlockBox expandBoxVertically(BlockBox box, int xOffset, int zOffset, int height) {
        int minY = box.min().getY();
        int maxY = box.max().getY();

        if (height < 0) {
            minY += (height + 1);
        } else if (height > 0) {
            maxY += (height - 1);
        }

        BlockPos min = new BlockPos(
                box.min().getX() - xOffset,
                minY,
                box.min().getZ() - zOffset
        );

        BlockPos max = new BlockPos(
                box.max().getX() + xOffset,
                maxY,
                box.max().getZ() + zOffset
        );

        return BlockBox.of(min, max);
    }

    /**
     * Creates a 3x3 square centered around (and expanding out from) the given block position.
     * @param pos The center position.
     * @return The square.
     */
    public static BlockBox squareAround(BlockPos pos) {
        return squareAround(pos, 1);
    }

    /**
     * Creates a square centered around (and expanding out from) the given block position with a defined offset size.
     * @param pos The center position.
     * @param offset how much the square extends (in all directions) from the center position. The diameter of the square is <code>2*offset + 1</code>.
     * @return The square.
     */
    public static BlockBox squareAround(BlockPos pos, int offset) {
        return rectangleAround(pos, offset, offset);
    }

    /**
     * Creates a rectangle centered around (and expanding out from) the given block position.
     * @param pos The center position.
     * @param xOffset how much the box extends along the x-axis (in both directions) from the center position.
     * @param zOffset how much the box extends along the z-axis (in both directions) from the center position.
     * @return The rectangle.
     */
    public static BlockBox rectangleAround(BlockPos pos, int xOffset, int zOffset) {
        return boxAround(pos, xOffset, 0, zOffset);
    }

    /**
     * Creates a 3x3x3 cube centered around (and expanding out from) the given block position.
     * @param pos The center position.
     * @return The box.
     */
    public static BlockBox cubeAround(BlockPos pos) {
        return cubeAround(pos, 1);
    }

    /**
     * Creates a cube centered around (and expanding out from) the given block position with a defined offset size.
     * @param pos The center position.
     * @param offset how much each cube side extends (in all directions) from the center position. The diameter of the cube is <code>2*offset + 1</code>
     * @return The box.
     */
    public static BlockBox cubeAround(BlockPos pos, int offset) {
        return boxAround(pos, offset, offset, offset);
    }

    /**
     * Creates a 3x3x3 cube starting (and extending downwards) from the given block position.
     * @param pos The center position.
     * @param height The height of the box.
     * @return The box.
     */
    public static BlockBox cubeBelow(BlockPos pos, int height) {
        return cubeBelow(pos, 1, height);
    }

    /**
     * Creates a box of a defined size starting (and extending downwards) from the given block position based on the defined height and offset.
     * @param pos The center position.
     * @param offset How much each cube side extends (in all directions) from the center position. The diameter of the cube is <code>2*offset + 1</code>
     * @param height The height of the box.
     * @return The box.
     */
    public static BlockBox cubeBelow(BlockPos pos, int offset, int height) {
        return boxBelow(pos, offset, offset, height);
    }

    /**
     * Creates a 3x3x3 cube starting (and extending upwards) from the given block position based on the defined height.
     * @param pos The center position.
     * @param height The height of the box.
     * @return The box.
     */
    public static BlockBox cubeAbove(BlockPos pos, int height) {
        return cubeAbove(pos, 1, height);
    }

    /**
     * Creates a box of a defined size starting (and extending upwards) from the given block position based on the defined height and offset.
     * @param pos The center position.
     * @param offset How much each cube side extends (in all directions) from the center position. The diameter of the cube is <code>2*offset + 1</code>
     * @param height The height of the box.
     * @return The box.
     */
    public static BlockBox cubeAbove(BlockPos pos, int offset, int height) {
        return boxAbove(pos, offset, offset, height);
    }

    /**
     * Creates a box of a defined size centered around (and expanding out from) the given block position.
     * @param pos The center position.
     * @param xOffset how much the box extends along the x-axis (in both directions) from the center position.
     * @param yOffset how much the box extends along the y-axis (in both directions) from the center position.
     * @param zOffset how much the box extends along the z-axis (in both directions) from the center position.
     * @return The box.
     */
    public static BlockBox boxAround(BlockPos pos, int xOffset, int yOffset, int zOffset) {
        BlockBox box = BlockBox.of(pos);
        return expandBox(box, xOffset, yOffset, zOffset);
    }

    /**
     * Creates a box of a defined size starting (and extending downwards) from the given block position.
     * @param pos The center position.
     * @param xOffset how much the box extends along the x-axis (in both directions) from the center position.
     * @param zOffset how much the box extends along the z-axis (in both directions) from the center position.
     * @param height The height of the box.
     * @return The box.
     */
    public static BlockBox boxBelow(BlockPos pos, int xOffset, int zOffset, int height) {
        BlockBox box = BlockBox.of(pos);

        if (height <= 0) {
            throw new IllegalArgumentException("Height must be above 0.");
        }

        return expandBoxVertically(box, xOffset, zOffset, -height);
    }

    /**
     * Creates a box of a defined size starting (and extending upwards) from the given block position.
     * @param pos The center position.
     * @param xOffset how much the box extends along the x-axis (in both directions) from the center position.
     * @param zOffset how much the box extends along the z-axis (in both directions) from the center position.
     * @param height The height of the box.
     * @return The box.
     */
    public static BlockBox boxAbove(BlockPos pos, int xOffset, int zOffset, int height) {
        BlockBox box = BlockBox.of(pos);

        if (height <= 0) {
            throw new IllegalArgumentException("Height must be above 0.");
        }

        return expandBoxVertically(box, xOffset, zOffset, height);
    }

    /**
     * A list of block positions in the provided block box.
     * @param box The box.
     * @return The positions.
     */
    public static List<BlockPos> positionsInBox(BlockBox box) {
        List<BlockPos> positions = new ArrayList<>();
        
        while (box.iterator().hasNext()) {
            BlockPos pos = box.iterator().next();
            positions.add(pos);
        }
        
        return positions;
    }

    /**
     * A list of block positions in the provided block box, excluding the number of inner layers specified by the depth.
     * @param box The box.
     * @param depth The depth of layers to "hollow out".
     * @return The positions.
     */
    public static List<BlockPos> positionsInHollowBox(BlockBox box, int depth) {
        List<BlockPos> inHollow = new ArrayList<>();

        // retrieve inner layers via contraction; the higher the depth number, the more layers are present.
        BlockBox core = contractBox(box, depth, depth, depth);
        
        for (BlockPos pos : box) {
            if (!core.contains(pos)) {
                inHollow.add(pos);
            }
        }
        
        return inHollow;
    }

    /**
     * Creates a block box from the given positions.
     * @param positions The block positions.
     * @return The box.
     */
    public static BlockBox boxFromPositions(List<BlockPos> positions) {
        BlockPos min = min(positions);
        BlockPos max = max(positions);
        return BlockBox.of(min, max);
    }

    /** @return the minimum position in the provided list. */
    public static BlockPos min(List<BlockPos> positions) {
        Optional<BlockPos> min = positions.stream().min(BlockPos::compareTo);
        return min.orElseThrow();
    }

    /** @return the maximum position in the provided list. */
    public static BlockPos max(List<BlockPos> positions) {
        Optional<BlockPos> max = positions.stream().max(BlockPos::compareTo);
        return max.orElseThrow();
    }

    /**
     * Distance formula between two block positions. Calls {@link BlockPos#distSqr(Vec3i)}.
     * @param start The starting position.
     * @param end The ending position.
     * @return The distance.
     */
    public static double distSqr(BlockPos start, BlockPos end) {
        return start.distSqr(end);
    }

    /**
     * Distance formula between two block positions along the horizontal axis.
     * @param start The starting position.
     * @param end The ending position.
     * @return The distance.
     */
    public static double horizontalDistSqr(BlockPos start, BlockPos end) {
        double xDistance = start.getX() - end.getX();
        double zDistance = start.getZ() - end.getZ();
        return xDistance * xDistance + zDistance * zDistance;
    }

    /**
     * Checks if a block is present at the given block position.
     * @param level The level.
     * @param pos The block position.
     * @return {@code true} if the position does not contain air, meaning there is a block at the position.
     */
    public static boolean containsAnyBlock(Level level, BlockPos pos) {
        return !level.getBlockState(pos).isAir();
    }

    /**
     * Checks if any block is present inside the given block box.
     * @param level The level.
     * @param box The block box.
     * @return {@code true} if any block position in the box is occupied by something besides air.
     */
    public static boolean containsAnyBlock(Level level, BlockBox box) {
        List<BlockPos> positions = positionsInBox(box);
        return positions.stream().anyMatch(pos -> containsAnyBlock(level, pos));
    }

    /**
     * Checks if a block of the given type is present at the given block position.
     * @param level The level.
     * @param pos The block position.
     * @param block The type of block.
     * @return {@code true} if the position contains a block of the given type.
     */
    public static boolean containsBlock(Level level, BlockPos pos, Block block) {
        return level.getBlockState(pos).is(block);
    }

    /**
     * Checks if a block of the given type is present in the block box.
     * @param level The level.
     * @param box The block box.
     * @param block The type of block.
     * @return {@code true} if the box contains a block of the given type.
     */
    public static boolean containsBlock(Level level, BlockBox box, Block block) {
        List<BlockPos> positions = positionsInBox(box);
        return positions.stream().anyMatch(pos -> containsBlock(level, pos, block));
    }

    /**
     * Map of the blocks (occupied block positions) within the given box.
     * @param level The level.
     * @param box The box.
     * @return The map, the keys of which equal the block positions, and the values equal the corresponding block states.
     */
    public static Map<BlockPos, BlockState> blocksInBox(Level level, BlockBox box) {
        List<BlockPos> positions = positionsInBox(box)
                .stream().filter(pos -> containsAnyBlock(level, pos))
                .toList();
        Map<BlockPos, BlockState> map = new HashMap<>();

        for (BlockPos pos : positions) {
            BlockState state = level.getBlockState(pos);
            map.put(pos, state);
        }

        return map;
    }

    /**
     * Map of the blocks (occupied block positions) of the specified type within the given box.
     * @param level The level.
     * @param box The box.
     * @param block The block type.
     * @return The map, the keys of which equal the block positions, and the values equal the corresponding block states.
     */
    public static Map<BlockPos, BlockState> blocksInBox(Level level, BlockBox box, Block block) {
        List<BlockPos> positions = positionsInBox(box)
                .stream().filter(pos -> containsBlock(level, pos, block))
                .toList();
        Map<BlockPos, BlockState> map = new HashMap<>();

        for (BlockPos pos : positions) {
            BlockState state = level.getBlockState(pos);
            map.put(pos, state);
        }

        return map;
    }


    /**
     * Checks if any block entity is present at the given block position.
     * @param level The level.
     * @param pos The block position.
     * @return {@code true} if the position contains a block entity.
     */
    public static boolean containsAnyBlockEntity(Level level, BlockPos pos) {
        return level.getBlockEntity(pos) != null;
    }

    /**
     * Checks if any block entity is present inside the given block box.
     * @param level The level.
     * @param box The block box.
     * @return {@code true} if the box contains a block entity.
     */
    public static boolean containsAnyBlockEntity(Level level, BlockBox box) {
        List<BlockPos> positions = positionsInBox(box);
        return positions.stream().anyMatch(pos -> containsAnyBlockEntity(level, pos));
    }

    /**
     * Checks if a block entity of the given type is present inside the given block position.
     * @param level The level.
     * @param pos The block box.
     * @param type The block entity type.
     * @return {@code true} if the position contains a block entity of the given type.
     */
    public static <T extends BlockEntity> boolean containsBlockEntity(Level level, BlockPos pos, BlockEntityType<T> type) {
        return level.getBlockEntity(pos, type).isPresent();
    }

    /**
     * Checks if a block entity of the given type is present inside the given block box.
     * @param level The level.
     * @param box The block box.
     * @param type The block entity type.
     * @return {@code true} if the box contains a block entity of the given type.
     */
    public static <T extends BlockEntity> boolean containsBlockEntity(Level level, BlockBox box, BlockEntityType<T> type) {
        List<BlockPos> positions = positionsInBox(box);
        return positions.stream().anyMatch(pos -> containsBlockEntity(level, pos, type));
    }

    /**
     * Map of the block entities (occupied block positions) within the given box.
     * @param level The level.
     * @param box The box.
     * @return The map, the keys of which equal the block positions, and the values equal the corresponding block entities.
     */
    public static Map<BlockPos, BlockEntity> blockEntitiesInBox(Level level, BlockBox box) {
        List<BlockPos> positions = positionsInBox(box)
                .stream().filter(pos -> containsAnyBlockEntity(level, box))
                .toList();
        Map<BlockPos, BlockEntity> map = new HashMap<>();

        for (BlockPos pos : positions) {
            BlockEntity entity = level.getBlockEntity(pos);
            map.put(pos, entity);
        }

        return map;
    }

    /**
     * Map of the block entities (occupied block positions) of the specified type within the given box.
     * @param level The level.
     * @param box The box.
     * @param type The block entity type.
     * @return The map, the keys of which equal the block positions, and the values equal the corresponding block entities.
     */
    public static Map<BlockPos, BlockEntity> blockEntitiesInBox(Level level, BlockBox box, BlockEntityType<? extends BlockEntity> type) {
        List<BlockPos> positions = positionsInBox(box)
                .stream().filter(pos -> containsBlockEntity(level, pos, type))
                .toList();
        Map<BlockPos, BlockEntity> map = new HashMap<>();

        for (BlockPos pos : positions) {
            BlockEntity entity = level.getBlockEntity(pos);
            map.put(pos, entity);
        }

        return map;
    }
}
