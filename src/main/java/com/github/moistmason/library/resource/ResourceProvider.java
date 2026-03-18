package com.github.moistmason.library.resource;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredBlock;

import static net.minecraft.resources.ResourceLocation.DEFAULT_NAMESPACE;

/**
 * <p> Helper methods for obtaining resource locations, both from vanilla and from your mod. </p>
 * <p> Usage: Simply call in an instance of this class, passing in your mod ID as the argument. This will let you easily
 * call any resource location in your mod. An example implementation can be found in the
 * {@link com.github.moistmason.library.data.LibraryBlockStateProvider} class.</p>
 * @author moist-mason
 */
public class ResourceProvider {
    private final String modId;

    public ResourceProvider(String modId) {
        this.modId = modId;
    }

    /** @return The resource location for the vanilla block ID.*/
    public ResourceLocation vanillaBlock(String id) {
        return vanillaResource("block/" + id);
    }

    /** @return The resource location for the mod block ID.*/
    public ResourceLocation modBlock(String id) {
        return modResource("block/" + id);
    }

    /** @return The resource location for the mod block ID, based on the provided block object.*/
    public ResourceLocation modBlock(DeferredBlock<?> block) {
        return modBlock(block.getId().getPath());
    }

    /** @return The resource location for the vanilla ID.*/
    public ResourceLocation vanillaResource(String id) {
        return ResourceLocation.fromNamespaceAndPath(DEFAULT_NAMESPACE, id);
    }

    /** @return The resource location for the mod ID.*/
    public ResourceLocation modResource(String id) {
        return ResourceLocation.fromNamespaceAndPath(modId, id);
    }
}
