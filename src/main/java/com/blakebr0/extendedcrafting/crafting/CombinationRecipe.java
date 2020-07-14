package com.blakebr0.extendedcrafting.crafting;

import com.blakebr0.extendedcrafting.config.ModConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.List;

public class CombinationRecipe {

	protected ItemStack output;
	protected long cost;
	protected int perTick;
	protected Ingredient input;
	protected NonNullList<Ingredient> pedestals;
	
	public CombinationRecipe(ItemStack output, long cost, Ingredient input, NonNullList<Ingredient> pedestals) {
		this(output, cost, ModConfig.confCraftingCoreRFRate, input, pedestals);
	}

	public CombinationRecipe(ItemStack output, long cost, int perTick, Ingredient input, NonNullList<Ingredient> pedestals) {
		this.output = output;
		this.cost = cost;
		this.perTick = perTick;
		this.input = input;
		this.pedestals = pedestals;
	}

	public ItemStack getOutput() {
		return this.output;
	}

	public long getCost() {
		return this.cost;
	}

	public int getPerTick() {
		return this.perTick;
	}

	public Ingredient getInput() {
		return this.input;
	}

	public List<Ingredient> getPedestalIngredients() {
		return this.pedestals;
	}

}
