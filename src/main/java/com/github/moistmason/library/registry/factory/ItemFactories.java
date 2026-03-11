package com.github.moistmason.library.registry.factory;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;

/**
 * Item factory interfaces.
 * @author moist-mason
 */
public class ItemFactories {

    /** Standard item factory. */
    @FunctionalInterface
    public interface ItemFactory<T extends Item> {
        T create(Properties properties);
    }

    /** Armor piece factory. */
    @FunctionalInterface
    public interface ArmorItemFactory<T extends ArmorItem> {
        T create(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties);
    }

    /** Generic tiered item factory. **/
    @FunctionalInterface
    public interface TieredItemFactory<T extends TieredItem> {
        T create(Tier tier, Properties properties);
    }

    /** Spawn egg factory. Note that since this is 1.21.1, the old spawn egg texture system is still used. **/
    @FunctionalInterface
    public interface SpawnEggItemFactory<T extends SpawnEggItem> {
        T create(EntityType<? extends Mob> type, int backgroundColor, int highlightColor, Properties properties);
    }
}
