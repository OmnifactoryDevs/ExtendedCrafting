package com.blakebr0.extendedcrafting.crafting;

import com.blakebr0.extendedcrafting.config.ModConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CompressorRecipeManager {

	private static final CompressorRecipeManager INSTANCE = new CompressorRecipeManager();

	private ArrayList<CompressorRecipe> recipes = new ArrayList<>();

	public static CompressorRecipeManager getInstance() {
		return INSTANCE;
	}
	
	public void addRecipe(ItemStack output, Ingredient input, int inputCount, Ingredient catalyst, boolean consumeCatalyst, int powerCost) {
		if (ModConfig.confCompressorEnabled) {
			this.recipes.add(new CompressorRecipe(output, input, inputCount, catalyst, consumeCatalyst, powerCost));
		}
	}

	public void addRecipe(ItemStack output, Ingredient input, int inputCount, Ingredient catalyst, boolean consumeCatalyst, int powerCost, int powerRate) {
		if (ModConfig.confCompressorEnabled) {
			this.recipes.add(new CompressorRecipe(output, input, inputCount, catalyst, consumeCatalyst, powerCost, powerRate));
		}
	}

	public List<CompressorRecipe> getRecipes() {
		return this.recipes;
	}

	public void removeRecipes(ItemStack stack) {
		this.recipes.removeIf(o -> o.getOutput().isItemEqual(stack));
	}
}
