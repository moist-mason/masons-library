package com.github.moistmason.library.resource;

import net.minecraft.resources.ResourceLocation;

import static net.minecraft.resources.ResourceLocation.DEFAULT_NAMESPACE;

/**
 * Helper methods for obtaining resource locations, both from vanilla and from your mod.
 * @author moist-mason
 */
public class ResourceProvider {
    private final String modId;

    public ResourceProvider(String modId) {
        this.modId = modId;
    }

    public ResourceLocation vanillaBlock(String id) {
        return vanillaResource("block/" + id);
    }

    public ResourceLocation modBlock(String id) {
        return modResource("block/" + id);
    }

    public ResourceLocation vanillaResource(String id) {
        return ResourceLocation.fromNamespaceAndPath(DEFAULT_NAMESPACE, id);
    }

    public ResourceLocation modResource(String id) {
        return ResourceLocation.fromNamespaceAndPath(modId, id);
    }
}
