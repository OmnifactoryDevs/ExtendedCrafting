package com.blakebr0.extendedcrafting.compat.gregtech;

import com.blakebr0.extendedcrafting.config.ModConfig;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface for accepting GTEU, built on top of an existing FE implementation.
 *
 * @see IGTCapHolder
 */
public interface IGTEnergyContainer extends IEnergyContainer, IGTCapHolder {
	IEnergyStorage getEnergy();

	@Nullable
	@Override
	default <T> T getGTCapability(@Nonnull Capability<T> capability) {
		if (capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) {
			return GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER.cast(this);
		}
		return null;
	}

	static int euToRF(long eu) {
		return (int) Math.min(Integer.MAX_VALUE / ModConfig.confEUtoRF, eu) * ModConfig.confEUtoRF;
	}

	static long rfToEU(int rf) {
		return (long) rf / ModConfig.confEUtoRF;
	}

	@Override
	default long acceptEnergyFromNetwork(EnumFacing side, long voltage, long amperage) {
		if (voltage <= 0 || amperage <= 0 || (side != null && !inputsEnergy(side))) return 0;

		long amperesAccepted = Math.min(getEnergyCanBeInserted() / voltage, Math.min(amperage, getInputAmperage()));
		if (amperesAccepted <= 0) return 0;

		addEnergy(voltage * amperesAccepted);
		return amperesAccepted;
	}

	@Override
	default boolean inputsEnergy(EnumFacing enumFacing) {
		return getEnergy().canReceive();
	}

	@Override
	default long changeEnergy(long difference) {
		return rfToEU(difference >= 0 ?
				getEnergy().receiveEnergy(euToRF(difference), false) :
				getEnergy().extractEnergy(euToRF(-difference), false));
	}

	@Override
	default long getEnergyStored() {
		return rfToEU(getEnergy().getEnergyStored());
	}

	@Override
	default long getEnergyCapacity() {
		return rfToEU(getEnergy().getMaxEnergyStored());
	}

	@Override
	default long getInputAmperage() {
		return Long.MAX_VALUE;
	}

	@Override
	default long getInputVoltage() {
		return Long.MAX_VALUE;
	}
}
