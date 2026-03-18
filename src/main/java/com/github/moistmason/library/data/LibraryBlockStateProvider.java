package com.github.moistmason.library.data;

import com.github.moistmason.library.resource.ResourceProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * <p>An implementation of NeoForge's existing {@link BlockStateProvider} class, with more concise/readable methods.</p>
 * <p>Usage: extend your mod's block state provider datagen class from this one, and call the methods as needed. </p>
 * @author moist-mason
 */
public abstract class LibraryBlockStateProvider extends BlockStateProvider {

    /** The resource provider. */
    private final ResourceProvider resourceProvider;

    public LibraryBlockStateProvider(PackOutput output, String modId, ExistingFileHelper fileHelper) {
        super(output, modId, fileHelper);
        this.resourceProvider = new ResourceProvider(modId);
    }

    /**
     * Creates block state data for a simple block.
     * @param block The block.
     * @param <T> The block type -> any full block whose texture is the same on all six sides.
     */
    protected <T extends Block> void block(DeferredBlock<T> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    /**
     * Creates block state data for a simple block with a texture separate from the block ID (i.e. wood and stripped wood).
     * @param block The block.
     * @param parentTexture The texture.
     * @param <T> The block type -> any full block whose texture is the same on all six sides.
     */
    protected <T extends Block> void block(DeferredBlock<T> block, ResourceLocation parentTexture) {
        simpleBlockWithItem(block.get(), models().singleTexture(block.getId().getPath(), vanillaBlock("cube_all"), "all", parentTexture));
    }

    /**
     * Creates block state data for a block that rotates along the vertical axis (i.e. logs).
     * @param block The block.
     * @param side The side texture.
     * @param end The bottom and top textures and model paths.
     * @param <T> The block type -> the {@link RotatedPillarBlock} class or one of its children.
     */
    protected <T extends RotatedPillarBlock> void axisBlock(DeferredBlock<T> block, ResourceLocation side, ResourceLocation end) {
        axisBlock(block.get(), side, end);
        blockItem(block);
    }

    /**
     * Creates block state data for a slab block who inherits a parent texture (usually from that of a full block).
     * @param block The block.
     * @param parent The parent texture and model path.
     * @param <T> The block type -> the {@link SlabBlock} class or one of its children.
     */
    protected <T extends SlabBlock> void slabBlock(DeferredBlock<T> block, ResourceLocation parent) {
        slabBlock(block, parent, parent, parent, parent);
    }

    /**
     * Creates block state data for a slab block with different models depending on whether it's double-stacked, and different textures for the sides and ends of the block.
     * @param block The block.
     * @param doubleSlab The model path for the double slab state.
     * @param side The side texture path.
     * @param end The bottom and top texture path.
     * @param <T> The block type -> the {@link SlabBlock} class or one of its children.
     */
    protected <T extends SlabBlock> void slabBlock(DeferredBlock<T> block, ResourceLocation doubleSlab, ResourceLocation side, ResourceLocation end) {
        slabBlock(block, doubleSlab, side, end, end);
    }

    /**
     * Creates block state data for a slab block with different models depending on whether it's double-stacked, and different textures for the sides, bottom, and top of the block.
     * @param block The block.
     * @param doubleSlab The model path for the double slab state.
     * @param side The side texture path.
     * @param bottom The bottom texture path.
     * @param top The top texture path.
     * @param <T> The block type -> the {@link SlabBlock} class or one of its children.
     */
    protected <T extends SlabBlock> void slabBlock(DeferredBlock<T> block, ResourceLocation doubleSlab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        slabBlock(block.get(), doubleSlab, side, bottom, top);
        blockItem(block);
    }

    /**
     * Creates block state data for a stair block who inherits a parent texture (usually from that of a full block).
     * @param block The block.
     * @param parent The parent texture and model path.
     * @param <T> The block type -> the {@link StairBlock} class or one of its children.
     */
    protected <T extends StairBlock> void stairsBlock(DeferredBlock<T> block, ResourceLocation parent) {
        stairsBlock(block, parent, parent, parent);
    }

    /**
     * Creates block state data for a stair block with different textures for the sides and ends.
     * @param block The block.
     * @param side The side texture path.
     * @param end The bottom and top texture path.
     * @param <T> The block type -> the {@link StairBlock} class or one of its children.
     */
    protected <T extends StairBlock> void stairsBlock(DeferredBlock<T> block, ResourceLocation side, ResourceLocation end) {
        stairsBlock(block, side, end, end);
    }

    /**
     * Creates block state data for a stair block with different textures for the sides and ends.
     * @param block The block.
     * @param side The side texture path.
     * @param bottom The bottom texture path.
     * @param top The Top texture path.
     * @param <T> The block type -> the {@link StairBlock} class or one of its children.
     */
    protected <T extends StairBlock> void stairsBlock(DeferredBlock<T> block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        stairsBlock(block.get(), side, bottom, top);
        blockItem(block);
    }

    /**
     * Creates block state data for a wall block.
     * @param block The block.
     * @param texture The texture path.
     * @param <T> The block type -> the {@link WallBlock} class or one of its children.
     */
    protected <T extends WallBlock> void wallBlock(DeferredBlock<T> block, ResourceLocation texture) {
        wallBlock(block.get(), texture);
        models().wallInventory(block.getId().getPath() + "_inventory", texture);
        blockItem(block, "inventory");
    }

    /**
     * Creates block state data for a fence block.
     * @param block The block.
     * @param texture The texture path.
     * @param <T> the block type -> the {@link FenceBlock} class or one of its children.
     */
    protected <T extends FenceBlock> void fenceBlock(DeferredBlock<T> block, ResourceLocation texture) {
        fenceBlock(block.get(), texture);
        models().fenceInventory(block.getId().getPath() + "_inventory", texture);
        blockItem(block, "inventory");
    }

    /**
     * Creates block state data for a fence gate block.
     * @param block The block.
     * @param texture The texture path.
     * @param <T> the block type -> the {@link FenceGateBlock} class or one of its children.
     */
    protected <T extends FenceGateBlock> void fenceGateBlock(DeferredBlock<T> block, ResourceLocation texture) {
        fenceGateBlock(block.get(), texture);
        blockItem(block);
    }

    /**
     * Creates block state data for a door block with a solid texture.
     * @param block The block.
     * @param bottom The texture and model path for the bottom of the door.
     * @param top The texture and model path for the top of the door.
     * @param <T> the block type -> the {@link DoorBlock} class or one of its children.
     */
    protected <T extends DoorBlock> void doorBlock(DeferredBlock<T> block, ResourceLocation bottom, ResourceLocation top) {
        doorBlock(block, bottom, top);
    }

    /**
     * Creates block state data for a door block with some of its model cut out (corresponding to transparent pixels on the texture).
     * @param block The block.
     * @param bottom The texture and model path for the bottom of the door.
     * @param top The texture and model path for the top of the door.
     * @param <T> the block type -> the {@link DoorBlock} class or one of its children.
     */
    protected <T extends DoorBlock> void doorBlockWithCutout(DeferredBlock<T> block, ResourceLocation bottom, ResourceLocation top) {
        doorBlockWithRenderType(block.get(), bottom, top, "cutout");
        itemModels().basicItem(block.getId());
    }

    /**
     * Creates block state data for a solid pane block.
     * @param block The block.
     * @param pane The texture path for the wide state of the pane.
     * @param edge The texure path for the skinny state of the pane.
     * @param <T> the block type -> the {@link IronBarsBlock} class or one of its children.
     */
    protected <T extends IronBarsBlock> void paneBlock(DeferredBlock<T> block, ResourceLocation pane, ResourceLocation edge) {
        paneBlock(block.get(), pane, edge);
        basicItemFromBlock(block);
    }

    /**
     * Creates block state data for a solid pane block with some of its model cut out (corresponding to transparent pixels on the texture).
     * @param block The block.
     * @param pane The texture path for the wide state of the pane.
     * @param edge The texure path for the skinny state of the pane.
     * @param <T> the block type -> the {@link IronBarsBlock} class or one of its children.
     */
    protected <T extends IronBarsBlock> void paneBlockWithCutout(DeferredBlock<T> block, ResourceLocation pane, ResourceLocation edge) {
        paneBlockWithRenderType(block.get(), pane, edge, "cutout");
        basicItemFromBlock(block);
    }

    /**
     * Creates block state data for a solid pane block that's transparent.
     * @param block The block.
     * @param pane The texture path for the wide state of the pane.
     * @param edge The texure path for the skinny state of the pane.
     * @param <T> the block type -> the {@link IronBarsBlock} class or one of its children.
     */
    protected <T extends IronBarsBlock> void transparentPaneBlock(DeferredBlock<T> block, ResourceLocation pane, ResourceLocation edge) {
        paneBlockWithRenderType(block.get(), pane, edge, "translucent");
        basicItemFromBlock(block);
    }

    /**
     * Creates block state data for a trapdoor block with a solid texture.
     * @param block The block.
     * @param texture The texture path.
     * @param <T> the block type -> the {@link TrapDoorBlock} class or one of its children.
     */
    protected <T extends TrapDoorBlock> void trapdoorBlock(DeferredBlock<T> block, ResourceLocation texture) {
        trapdoorBlock(block, texture, true);
    }

    /**
     * Creates block state data for a trapdoor block with a solid texture.
     * @param block The block.
     * @param texture The texture path.
     * @param orientable I don't know :sob:
     * @param <T> the block type -> the {@link TrapDoorBlock} class or one of its children.
     */
    protected <T extends TrapDoorBlock> void trapdoorBlock(DeferredBlock<T> block, ResourceLocation texture, boolean orientable) {
        trapdoorBlock(block.get(), texture, orientable);
        blockItem(block);
    }

    /**
     * Creates block state data for a trapdoor block with some of its model cut out (corresponding to transparent pixels on the texture).
     * @param block The block.
     * @param texture The texture path.
     * @param <T> the block type -> the {@link TrapDoorBlock} class or one of its children.
     */
    protected <T extends TrapDoorBlock> void trapdoorBlockWithCutout(DeferredBlock<T> block, ResourceLocation texture) {
        trapdoorBlockWithCutout(block, texture, true);
    }

    /**
     * Creates block state data for a trapdoor block with some of its model cut out (corresponding to transparent pixels on the texture).
     * @param block The block.
     * @param texture The texture path.
     * @param orientable I don't know :sob:
     * @param <T> the block type -> the {@link TrapDoorBlock} class or one of its children.
     */
    protected <T extends TrapDoorBlock> void trapdoorBlockWithCutout(DeferredBlock<T> block, ResourceLocation texture, boolean orientable) {
        trapdoorBlockWithRenderType(block.get(), texture, orientable, "cutout");
        blockItem(block);
    }

    /**
     * Creates a simple block item model.
     * @param block The block.
     * @param <T> the block type.
     */
    protected <T extends Block> void blockItem(DeferredBlock<T> block) {
        blockItem(block, null);
    }

    /**
     * Creates a simple block item model whose model does not correspond to the block's ID.
     * @param block The block.
     * @param appendix additional data to append on the model path.
     * @param <T> the block type.
     */
    protected <T extends Block> void blockItem(DeferredBlock<T> block, String appendix) {
        String id = appendix != null ? block.getId().getPath() + "_" + appendix : block.getId().getPath();
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(modBlock(id)));
    }

    /**
     * Creates a block item model whose texture is the specified block resource location. Used for items such as glass panes that
     * use raw block textures while in the inventory. This specific method allows the user to select a specific texture if calling a multi-texture block.
     * The syntax is lifted from a method in {@link net.neoforged.neoforge.client.model.generators.ItemModelProvider}.
     * @return the item model.
     * @see net.neoforged.neoforge.client.model.generators.ItemModelProvider#basicItem(ResourceLocation)
     */
    protected ItemModelBuilder basicItemFromBlock(DeferredBlock<?> block, ResourceLocation texture) {
        String id = block.getId().getPath();
        return itemModels().getBuilder("item/" + id)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", texture);
    }

    /**
     * Creates a block item model whose texture is the sole texture from the given block. Used for items such as glass panes that
     * use raw block textures while in the inventory.
     * The syntax is lifted from a method in {@link net.neoforged.neoforge.client.model.generators.ItemModelProvider}.
     * @return the item model.
     * @see net.neoforged.neoforge.client.model.generators.ItemModelProvider#basicItem(ResourceLocation)
     */
    protected ItemModelBuilder basicItemFromBlock(DeferredBlock<?> block) {
        return basicItemFromBlock(block, block.getId());
    }

    protected ResourceLocation vanillaBlock(String id) {
        return resourceProvider.vanillaBlock(id);
    }

    protected ResourceLocation modBlock(String id) {
        return resourceProvider.modBlock(id);
    }

    protected ResourceLocation modBlock(DeferredBlock<?> block) {
        return resourceProvider.modBlock(block);
    }
}
