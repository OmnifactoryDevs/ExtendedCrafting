package com.blakebr0.extendedcrafting.crafting;

import com.blakebr0.extendedcrafting.config.ModConfig;
import com.blakebr0.extendedcrafting.lib.AddonReferenced;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AddonReferenced
public class CombinationRecipe {

	protected final ItemStack output;
	protected final long cost;
	protected final int perTick;
	protected final Ingredient input;
	protected final NonNullList<Ingredient> pedestals;
	
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

	@AddonReferenced
	public ItemStack getOutput() {
		return this.output;
	}

	@AddonReferenced
	public long getCost() {
		return this.cost;
	}

	@AddonReferenced
	public int getPerTick() {
		return this.perTick;
	}

	public Ingredient getInputIngredient() {
		return this.input;
	}

	@AddonReferenced
	public ItemStack getInput() {
		return input.getMatchingStacks()[0];
	}

	public List<Ingredient> getPedestalIngredients() {
		return this.pedestals;
	}

	@AddonReferenced
	public ArrayList<ItemStack> getPedestalItems() {
		return getPedestalIngredients()
				.stream()
				.map(Ingredient::getMatchingStacks)
				.map(stacks -> stacks[0])
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
