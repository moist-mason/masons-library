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
 * <p>Usage: extend your mod's block state provider datagen class from this one.</p>
 * @author moist-mason
 */
public abstract class LibraryBlockStateProvider extends BlockStateProvider {
    private final ResourceProvider resourceProvider;

    public LibraryBlockStateProvider(PackOutput output, String modId, ExistingFileHelper fileHelper) {
        super(output, modId, fileHelper);
        this.resourceProvider = new ResourceProvider(modId);
    }

    protected <T extends Block> void block(DeferredBlock<T> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    protected <T extends Block> void block(DeferredBlock<T> block, ResourceLocation parentTexture) {
        simpleBlockWithItem(block.get(), models().singleTexture(block.getId().getPath(), vanillaBlock("cube_all"), "all", parentTexture));
    }

    protected <T extends RotatedPillarBlock> void axisBlock(DeferredBlock<T> block, ResourceLocation side, ResourceLocation end) {
        axisBlock(block.get(), side, end);
        blockItem(block);
    }

    protected <T extends SlabBlock> void slabBlock(DeferredBlock<T> block, ResourceLocation parent) {
        slabBlock(block, parent, parent, parent, parent);
    }

    protected <T extends SlabBlock> void slabBlock(DeferredBlock<T> block, ResourceLocation doubleSlab, ResourceLocation side, ResourceLocation end) {
        slabBlock(block, doubleSlab, side, end, end);
    }

    protected <T extends SlabBlock> void slabBlock(DeferredBlock<T> block, ResourceLocation doubleSlab, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        slabBlock(block.get(), doubleSlab, side, bottom, top);
        blockItem(block);
    }

    protected <T extends StairBlock> void stairsBlock(DeferredBlock<T> block, ResourceLocation texture) {
        stairsBlock(block, texture, texture, texture);
    }

    protected <T extends StairBlock> void stairsBlock(DeferredBlock<T> block, ResourceLocation side, ResourceLocation end) {
        stairsBlock(block, side, end, end);
    }

    protected <T extends StairBlock> void stairsBlock(DeferredBlock<T> block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        stairsBlock(block.get(), side, bottom, top);
        blockItem(block);
    }

    protected <T extends WallBlock> void wallBlock(DeferredBlock<T> block, ResourceLocation texture) {
        wallBlock(block.get(), texture);
        models().wallInventory(block.getId().getPath() + "_inventory", texture);
        blockItem(block, "inventory");
    }

    protected <T extends FenceBlock> void fenceBlock(DeferredBlock<T> block, ResourceLocation texture) {
        fenceBlock(block.get(), texture);
        models().fenceInventory(block.getId().getPath() + "_inventory", texture);
        blockItem(block, "inventory");
    }

    protected <T extends FenceGateBlock> void fenceGateBlock(DeferredBlock<T> block, ResourceLocation texture) {
        fenceGateBlock(block.get(), texture);
        blockItem(block);
    }

    protected <T extends DoorBlock> void doorBlock(DeferredBlock<T> block, ResourceLocation bottom, ResourceLocation top) {
        doorBlock(block.get(), bottom, top);
        itemModels().basicItem(block.getId());
    }

    protected <T extends IronBarsBlock> void paneBlock(DeferredBlock<T> block, ResourceLocation pane, ResourceLocation edge) {
        paneBlock(block.get(), pane, edge);
        basicItemFromBlock(modBlock(block));
    }

    protected <T extends TrapDoorBlock> void trapdoorBlock(DeferredBlock<T> block, ResourceLocation texture) {
        trapdoorBlock(block, texture, true);
    }

    protected <T extends TrapDoorBlock> void trapdoorBlock(DeferredBlock<T> block, ResourceLocation texture, boolean orientable) {
        trapdoorBlock(block.get(), texture, orientable);
        blockItem(block);
    }

    protected <T extends Block> void blockItem(DeferredBlock<T> block) {
        blockItem(block, null);
    }

    protected <T extends Block> void blockItem(DeferredBlock<T> block, String appendix) {
        String id = appendix != null ? block.getId().getPath() + "_" + appendix : block.getId().getPath();
        simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(modBlock(id)));
    }

    /**
     * @return an item model whose texture is the given block texture location. Used for items such as glass panes that
     * use raw block textures while in the inventory. Note that this assumes "block/" is prepended to the resource location path,
     * so it's best to use {@link LibraryBlockStateProvider#modBlock(DeferredBlock)} or {@link LibraryRecipeProvider#modResource(String)} when passing in a resource location to this method.
     * The syntax is lifted from a method in {@link net.neoforged.neoforge.client.model.generators.ItemModelProvider}.
     * @see net.neoforged.neoforge.client.model.generators.ItemModelProvider#basicItem(ResourceLocation)
     */
    protected ItemModelBuilder basicItemFromBlock(ResourceLocation block) {
        return itemModels().getBuilder("item/" + block.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", block);
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
