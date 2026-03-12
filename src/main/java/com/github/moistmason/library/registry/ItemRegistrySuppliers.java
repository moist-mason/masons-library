package com.github.moistmason.library.registry;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.Supplier;

import static com.github.moistmason.library.registry.factory.ItemFactories.*;

//TODO: rework this class probably. Item registration seems a little different than I thought :/

/**
 * Supplier helper methods for item registration.
 * @author moist-mason
 */
public class ItemRegistrySuppliers {
    public static Supplier<Item> supply(Properties properties) {
        return supply(Item::new, properties);
    }

    public static <T extends Item> Supplier<T> supply(ItemFactory<T> factory, Properties properties) {
        return () -> factory.create(properties);
    }

    public static <T extends ArmorItem> Supplier<T> supplyArmorPiece(ArmorItemFactory<T> factory, Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties, int maxDamage) {
        return () -> factory.create(material, type, properties.durability(maxDamage));
    }

    public static <T extends Item> Supplier<T> supplyFood(ItemFactory<T> factory, Properties properties, FoodProperties foodProperties) {
        return () -> factory.create(properties.food(foodProperties));
    }

    public static <T extends TieredItem> Supplier<T> supplyTieredItem(TieredItemFactory<T> factory, Tier tier, Properties properties, ItemAttributeModifiers attributes) {
        return () -> factory.create(tier, properties.attributes(attributes));
    }

    public static <T extends SpawnEggItem> Supplier<T> supplySpawnEgg(SpawnEggItemFactory<T> factory, EntityType<? extends Mob> entity, int backgroundColor, int highlightColor, Properties properties) {
        return () -> factory.create(entity, backgroundColor, highlightColor, properties);
    }
}
