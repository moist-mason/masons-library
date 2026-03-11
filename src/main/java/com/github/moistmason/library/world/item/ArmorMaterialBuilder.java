package com.github.moistmason.library.world.item;

import com.github.moistmason.library.resource.ResourceProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * Builder class for armor materials.
 * @author moist-mason
 */
public class ArmorMaterialBuilder {
    private final ResourceProvider resourceProvider;

    private ResourceLocation location;
    private EnumMap<Type, Integer> protection;
    private int enchantability;
    private float toughness;
    private float knockbackResistance;
    private Supplier<Ingredient> ingredient;
    private Holder<SoundEvent> sound;

    public ArmorMaterialBuilder(String modId) {
        this.resourceProvider = new ResourceProvider(modId);
    }

    public ArmorMaterialBuilder location(String name) {
        this.location = resourceProvider.modResource(name);
        return this;
    }

    public ArmorMaterialBuilder protection(EnumMap<Type, Integer> protection) {
        this.protection = protection;
        return this;
    }

    public ArmorMaterialBuilder protection(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public ArmorMaterialBuilder toughness(float toughness) {
        this.toughness = toughness;
        return this;
    }

    public ArmorMaterialBuilder knockbackResistance(float knockbackResistance) {
        this.knockbackResistance = knockbackResistance;
        return this;
    }

    public ArmorMaterialBuilder ingredient(Supplier<Ingredient> ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public ArmorMaterialBuilder ingredient(Ingredient ingredient) {
        this.ingredient = () -> ingredient;
        return this;
    }

    public ArmorMaterialBuilder ingredient(ItemLike item) {
        this.ingredient = () -> Ingredient.of(item);
        return this;
    }

    public ArmorMaterialBuilder ingredient(TagKey<Item> tag) {
        this.ingredient = () -> Ingredient.of(tag);
        return this;
    }

    public ArmorMaterialBuilder sound(Holder<SoundEvent> sound) {
        this.sound = sound;
        return this;
    }

    public <T extends SoundEvent> ArmorMaterialBuilder sound(DeferredHolder<SoundEvent, T> sound) {
        this.sound = sound.getDelegate();
        return this;
    }

    public Holder<ArmorMaterial> build() {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(protection, enchantability, sound, ingredient, layers, toughness, knockbackResistance));
    }
}
