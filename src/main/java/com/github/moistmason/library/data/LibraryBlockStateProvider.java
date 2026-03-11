package com.github.moistmason.library.data;

import com.github.moistmason.library.resource.ResourceProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

/**
 * <p>An implementation of NeoForge's existing {@link BlockStateProvider} class, with more concise/readable methods.</p>
 * <p>Usage: extend your mod's block state provider datagen class from this one; make sure to define the {@link ResourceProvider} first.</p>
 * @author moist-mason
 */
public abstract class LibraryBlockStateProvider extends BlockStateProvider {
    private final ResourceProvider resourceProvider;

    public LibraryBlockStateProvider(PackOutput output, String modId, ExistingFileHelper fileHelper, ResourceProvider resourceProvider) {
        super(output, modId, fileHelper);
        this.resourceProvider = resourceProvider;
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
    }

    protected <T extends IronBarsBlock> void paneBlock(DeferredBlock<T> block, ResourceLocation pane, ResourceLocation edge) {
        paneBlock(block.get(), pane, edge);
        blockItem(block);
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

    protected ResourceLocation vanillaBlock(String id) {
        return resourceProvider.vanillaBlock(id);
    }

    protected ResourceLocation modBlock(String id) {
        return resourceProvider.modBlock(id);
    }
}
