package com.github.moistmason.library.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Registry parsing methods, for both vanilla and mod registries.
 * @author moist-mason
 */
public class RegistryUtil {

    /** @return the given vanilla registry as a list. */
    public static <T> List<T> toList(Registry<T> registry) {
        return registry.stream().toList();
    }

    /** @return the given vanilla registry as a list, filtered based on the given predicate. */
    public static <T> List<T> toList(Registry<T> registry, Predicate<T> predicate) {
        return registry.stream().filter(predicate).toList();
    }

    /** @return the given mod registry as a list. */
    public static <T> List<T> toList(DeferredRegister<T> registry) {
        return toList(registry, null);
    }

    /** @return the given mod registry as a list, filtered based on the given predicate. */
    public static <T> List<T> toList(DeferredRegister<T> registry, Predicate<T> predicate) {
        List<T> list = new LinkedList<>();

        for (DeferredHolder<T, ? extends T> entry : registry.getEntries()) {
            list.add(entry.get());
        }

        if (predicate != null) {
            list = list.stream().filter(predicate).toList();
        }

        return list;
    }

    /** @return the given vanilla registry as a map, with its keys being the in-game String IDs, and its values being the corresponding entries in the registry. */
    public static <T> Map<String, T> toMap(Registry<T> registry) {
        Map<String, T> map = new LinkedHashMap<>();

        for (Map.Entry<ResourceKey<T>, T> entry : registry.entrySet()) {
            String id = entry.getKey().location().getPath();
            T item = entry.getValue();
            map.put(id, item);
        }

        return map;
    }

    /** @return the given mod registry as a map, with its keys being the string IDs, and its values being the corresponding entries in the registry. */
    public static <T> Map<String, T> toMap(DeferredRegister<T> registry) {
        Map<String, T> map = new LinkedHashMap<>();

        for (DeferredHolder<T, ? extends T> entry : registry.getEntries()) {
            String id = entry.getId().getPath();
            T item = entry.get();
            map.put(id, item);
        }

        return map;
    }

    /** @return the given vanilla registry as a map, with its keys being the registry items, and its values being the corresponding string IDs. */
    public static <T> Map<T, String> toIdMap(Registry<T> registry) {
        Map<String, T> valueMap = toMap(registry);
        return invertedMap(valueMap);
    }

    /** @return the given mod registry as a map, with its keys being the registry items, and its values being the corresponding string IDs. */
    public static <T> Map<T, String> toIdMap(DeferredRegister<T> registry) {
        Map<String, T> valueMap = toMap(registry);
        return invertedMap(valueMap);
    }

    /** @return an inverted map. Keys of the original map become values, and vice versa. */
    private static <V, K> Map<V, K> invertedMap(Map<K, V> original) {
        Map<V, K> inverted = new LinkedHashMap<>();
        original.forEach((key, value) -> inverted.put(value, key));
        return inverted;
    }

    /** @return the object in the vanilla registry with the provided ID. */
    public static <T> T get(Registry<T> registry, String id) {
        Map<String, T> map = toMap(registry);
        return map.get(id);
    }

    /** @return the object in the mod registry with the provdied ID. */
    public static <T> T get(DeferredRegister<T> registry, String id) {
        Map<String, T> map = toMap(registry);
        return map.get(id);
    }

    /** @return the ID of the provided object in the vanilla registry. */
    public static <T> String getId(Registry<T> registry, T item) {
        Map<T, String> map = toIdMap(registry);
        return map.get(item);
    }

    /** @return the ID of the provided object in the mod registry. */
    public static <T> String getId(DeferredRegister<T> registry, T item) {
        Map<T, String> map = toIdMap(registry);
        return map.get(item);
    }
}
