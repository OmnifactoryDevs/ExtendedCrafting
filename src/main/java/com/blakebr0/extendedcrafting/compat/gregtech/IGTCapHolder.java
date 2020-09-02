package com.blakebr0.extendedcrafting.compat.gregtech;

import gregtech.api.capability.GregtechCapabilities;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface for blocks that may have the
 * {@link GregtechCapabilities#CAPABILITY_ENERGY_CONTAINER}
 * capability.
 *
 * Implementing classes are expected to implement the {@link IGTEnergyContainer}
 * interface as well, but using {@link net.minecraftforge.fml.common.Optional.Interface}.
 *
 * This class ensures that when the {@link IGTEnergyContainer} interface is stripped,
 * {@link #getGTCapability(Capability)} is still available.
 */
public interface IGTCapHolder {
	/**
	 * Get the {@link GregtechCapabilities#CAPABILITY_ENERGY_CONTAINER} capability, if
	 * it is being requested.
	 *
	 * This will be the implementation used if GregTech is not present.
	 */
	@Nullable
	default <T> T getGTCapability(@Nonnull Capability<T> capability) {
		return null;
	}
}
