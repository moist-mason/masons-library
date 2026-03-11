package com.github.moistmason.library.world.item;

import net.minecraft.Util;
import net.minecraft.world.item.ArmorItem.Type;

import java.util.EnumMap;

/**
 * Builder class for armor protection values registered in armor material.
 * @author moist-mason
 */
public class ArmorProtectionMapBuilder {
    private int helmet;
    private int chestplate;
    private int leggings;
    private int boots;
    private int body;

    public ArmorProtectionMapBuilder helmet(int helmet) {
        this.helmet = helmet;
        return this;
    }

    public ArmorProtectionMapBuilder chestplate(int chestplate) {
        this.chestplate = chestplate;
        return this;
    }

    public ArmorProtectionMapBuilder leggings(int leggings) {
        this.leggings = leggings;
        return this;
    }

    public ArmorProtectionMapBuilder boots(int boots) {
        this.boots = boots;
        return this;
    }

    public ArmorProtectionMapBuilder body(int body) {
        this.body = body;
        return this;
    }

    public EnumMap<Type, Integer> build() {
        return Util.make(new EnumMap<>(Type.class), attribute -> {
            attribute.put(Type.HELMET, helmet);
            attribute.put(Type.CHESTPLATE, chestplate);
            attribute.put(Type.LEGGINGS, leggings);
            attribute.put(Type.BOOTS, boots);
            attribute.put(Type.BODY, body);
        });
    }
}
