package com.github.moistmason.library.registry;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

import static com.github.moistmason.library.registry.factory.ItemFactories.*;

public class ItemRegistrySuppliers {
    public static Supplier<Item> supply(Properties properties) {
        return supply(Item::new, properties);
    }

    public static <T extends Item> Supplier<T> supply(ItemFactory<T> factory, Properties properties) {
        return () -> factory.create(properties);
    }

    public static <T extends ArmorItem> Supplier<T> supplyArmorPiece(ArmorPieceFactory<T> factory, Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties) {
        return () -> factory.create(material, type, properties);
    }

    public static <T extends SpawnEggItem> Supplier<T> supplySpawnEgg(SpawnEggFactory<T> factory, EntityType<? extends Mob> entity, int backgroundColor, int highlightColor, Properties properties) {
        return () -> factory.create(entity, backgroundColor, highlightColor, properties);
    }
}
