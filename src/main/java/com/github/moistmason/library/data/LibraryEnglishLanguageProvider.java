package com.github.moistmason.library.data;

import com.github.moistmason.library.registry.RegistryUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

/**
 * <p>Utility class for data generation of the English language JSON (en_us.json). Extends NeoForge's {@link LanguageProvider}.
 * This class contains methods that let you parse an entire registry and convert its IDs into
 * readable in-game names. </p>
 */
public abstract class LibraryEnglishLanguageProvider extends LanguageProvider {
    private final String modId;

    public LibraryEnglishLanguageProvider(PackOutput output, String modId) {
        super(output, modId, "en_us");
        this.modId = modId;
    }

    /** Adds an entire registry for translation. */
    protected <T> void add(DeferredRegister<T> registry, String category) {
        add(registry, category, null);
    }

    /**
     * Adds a registry for translation, filtered based on the given predicate. The predicate system is useful if some display names are different
     * from their IDs (the ore blocks, for example). You can filter those out from this method and then add them manually later.
     */
    protected <T> void add(DeferredRegister<T> registry, String category, Predicate<T> predicate) {
        List<T> list = RegistryUtil.toList(registry, predicate);

        for (T item : list) {
            String id = RegistryUtil.getId(registry, item);
            String value = toValue(id);
            String key = String.join(".", category, modId, id);
            add(key, value);
        }
    }

    protected <T extends CreativeModeTab> void addCreativeTab(DeferredHolder<CreativeModeTab, T> creativeTab, String name) {
        String key = String.join(".", "itemGroup", creativeTab.getId().getNamespace(), creativeTab.getId().getPath());
        add(key, name);
    }

    /** Converts an ID to its English translation. */
    protected String toValue(String key) {
        List<String> words = new LinkedList<>();
        String spaced = key.replace('_', ' ');
        String[] split = spaced.split(" ");

        for (String word : split) {
            word = uncapitalized().contains(word) ? word : capitalize(word);
            words.add(word);
        }

        return String.join(" ", words);
    }

    protected String capitalize(String data) {
        return data.substring(0, 1).toUpperCase(Locale.ROOT) + data.substring(1);
    }

    /** @return a list of common words that usually aren't capitalized in a title. */
    protected List<String> uncapitalized() {
        return List.of("a", "an", "the", "and", "but", "of", "at", "by", "from", "to", "on", "up", "for");
    }
}
