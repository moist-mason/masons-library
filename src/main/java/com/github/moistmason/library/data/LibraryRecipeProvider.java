package com.github.moistmason.library.data;

import com.github.moistmason.library.registry.RegistryUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.concurrent.CompletableFuture;

/**
 * <p>Utility class that more concise methods for recipe datagen. The vanilla RecipeProvider class already has several helper methods for commonly used recipes,
 * but they often still require additional manual input, like criteria for unlocking the recipe in the recipe book. This class does all that for you. </p>
 * <p>Usage: extend your mod's recipe provider class from this class. Then, when calling the super in your mod class's constructor,
 * pass in your mod's item registry field in the {@code itemRegistry} parameter. </p>
 */
public abstract class LibraryRecipeProvider extends RecipeProvider {

    /** The mod's item registry. */
    private final DeferredRegister.Items itemRegistry;

    public LibraryRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, DeferredRegister.Items itemRegistry) {
        super(output, registries);
        this.itemRegistry = itemRegistry;
    }

    /** Creates a two-by-two recipe consisting of a single ingredient. */
    protected void twoSquaredRecipe(RecipeOutput output, RecipeCategory category, ItemLike material, ItemLike result) {
        twoSquaredRecipe(output, category, material, result, 1);
    }

    /** Creates a two-by-two recipe consisting of a single ingredient. */
    protected void twoSquaredRecipe(RecipeOutput output, RecipeCategory category, ItemLike material, ItemLike result, int count) {
        ShapedRecipeBuilder.shaped(category, result, count)
                .pattern("##")
                .pattern("##")
                .define('#', material)
                .unlockedBy(hasName(material), has(material.asItem())).save(output);
    }

    /** Creates a three-by-three recipe consisting of a single ingredient. */
    protected void threeSquaredRecipe(RecipeOutput output, RecipeCategory category, ItemLike material, ItemLike result) {
        ShapedRecipeBuilder.shaped(category, result)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', material)
                .unlockedBy(hasName(material), has(material.asItem())).save(output);
    }

    /** Creates a simple slab recipe. */
    protected void slabRecipe(RecipeOutput output, ItemLike material, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .pattern("###")
                .define('#', material)
                .unlockedBy(hasName(material), has(material.asItem())).save(output);
    }

    /** Creates a simple stair recipe. */
    protected void stairRecipe(RecipeOutput output, ItemLike material, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material)).save(output);
    }

    /** Creates a simple wall recipe. */
    protected void wallRecipe(RecipeOutput output, ItemLike material, ItemLike result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .pattern("###")
                .pattern("###")
                .define('#', material)
                .unlockedBy(getHasName(material), has(material)).save(output);
    }

    /**
     * Creates a simple smelting recipe that is 10 seconds in length. This calls {@code smeltingResultFromBase}, a vanilla method that defines
     * a simple recipe in the building blocks recipe category.
     */
    protected void simpleSmeltingRecipe(RecipeOutput output, ItemLike material, ItemLike result) {
        smeltingResultFromBase(output, material, result);
    }

    /**
     * Creates a simple smelting recipe that is 10 seconds in length. This is similar to {@code smeltingResultFromBase}, but allows the user to
     * also define the recipe category.
     */
    protected void simpleSmeltingRecipe(RecipeOutput output, RecipeCategory category, ItemLike material, ItemLike result) {
        smeltingRecipe(output, category, material, result, 0.1F, 200);
    }

    /** Creates a simple smelting recipe with a definable XP reward and smelting time. */
    protected void smeltingRecipe(RecipeOutput output, RecipeCategory category, ItemLike material, ItemLike result, float xp, int time) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(material), category, result, xp, time).save(output);
    }

    /**
     * @return "has_" + the ID of the provided item. This checks through either the vanilla or mod item registries to find the correct ID. This is a similar method to
     * {@code RecipeProvider.getHasName}.
     */
    protected String hasName(ItemLike item) {
        return BuiltInRegistries.ITEM.containsValue(item.asItem())
                ? "has_" + BuiltInRegistries.ITEM.getKey(item.asItem()).getPath()
                : "has_" + RegistryUtil.getId(itemRegistry, item.asItem());
    }
}
