package com.github.moistmason.library.registry.factory;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SpawnEggItem;

public class ItemFactories {
    @FunctionalInterface
    public interface ItemFactory<T extends Item> {
        T create(Properties properties);
    }

    @FunctionalInterface
    public interface ArmorPieceFactory<T extends ArmorItem> {
        T create(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties);
    }

    @FunctionalInterface
    public interface SpawnEggFactory<T extends SpawnEggItem> {
        T create(EntityType<? extends Mob> type, int backgroundColor, int highlightColor, Properties properties);
    }
}
