package com.blakebr0.extendedcrafting.crafting;

import com.blakebr0.extendedcrafting.config.ModConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CompressorRecipe {

	protected final ItemStack output;
	protected final Ingredient input;
	protected final int inputCount;
	protected final Ingredient catalyst;
	protected final boolean consumeCatalyst;
	protected final int powerCost;
	protected final int powerRate;
	
	public CompressorRecipe(ItemStack output, Ingredient input, int inputCount, Ingredient catalyst, boolean consumeCatalyst, int powerCost) {
		this(output, input, inputCount, catalyst, consumeCatalyst, powerCost, ModConfig.confCompressorRFRate);
	}

	public CompressorRecipe(ItemStack output, Ingredient input, int inputCount, Ingredient catalyst, boolean consumeCatalyst, int powerCost, int powerRate) {
		this.output = output;
		this.input = input;
		this.inputCount = inputCount;
		this.catalyst = catalyst;
		this.consumeCatalyst = consumeCatalyst;
		this.powerCost = powerCost;
		this.powerRate = powerRate;
	}

	public ItemStack getOutput() {
		return this.output.copy();
	}

	public Ingredient getInput() {
		return this.input;
	}

	public int getInputCount() {
		return this.inputCount;
	}

	public Ingredient getCatalyst() {
		return this.catalyst;
	}

	public boolean consumeCatalyst() {
		return this.consumeCatalyst;
	}

	public int getPowerCost() {
		return this.powerCost;
	}
	
	public int getPowerRate() {
		return this.powerRate;
	}
}
