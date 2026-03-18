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

/**
 * Supplier helper methods for item registration.
 * @author moist-mason
 */
public class ItemRegistrySuppliers {

    /** Creates a simple item with no properties. */
    public static Supplier<Item> supply() {
        return supply(Item::new, new Properties());
    }

    /** Creates a simple item with defined properties. */
    public static Supplier<Item> supply(Properties properties) {
        return supply(Item::new, properties);
    }

    /** Creates an item with defined properties. **/
    public static <T extends Item> Supplier<T> supply(ItemFactory<T> factory, Properties properties) {
        return () -> factory.create(properties);
    }

    /** Creates an armor item. **/
    public static <T extends ArmorItem> Supplier<T> supplyArmorPiece(ArmorItemFactory<T> factory, Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties, int maxDamage) {
        return () -> factory.create(material, type, properties.durability(maxDamage));
    }

    /** Creates a food item. **/
    public static <T extends Item> Supplier<T> supplyFood(ItemFactory<T> factory, Properties properties, FoodProperties foodProperties) {
        return () -> factory.create(properties.food(foodProperties));
    }

    /** Creates a tiered item. **/
    public static <T extends TieredItem> Supplier<T> supplyTieredItem(TieredItemFactory<T> factory, Tier tier, Properties properties, ItemAttributeModifiers attributes) {
        return () -> factory.create(tier, properties.attributes(attributes));
    }

    /** Creates a spawn egg item. Note that since this is 1.21.1, the old spawn egg texture system is still used. **/
    public static <T extends SpawnEggItem> Supplier<T> supplySpawnEgg(SpawnEggItemFactory<T> factory, EntityType<? extends Mob> entity, int backgroundColor, int highlightColor, Properties properties) {
        return () -> factory.create(entity, backgroundColor, highlightColor, properties);
    }
}
