package com.blakebr0.extendedcrafting.crafting;

import com.blakebr0.extendedcrafting.config.ModConfig;
import com.blakebr0.extendedcrafting.lib.AddonReferenced;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

@AddonReferenced
public class CombinationRecipeManager {

	private static final CombinationRecipeManager INSTANCE = new CombinationRecipeManager();

	private final ArrayList<CombinationRecipe> recipes = new ArrayList<>();

	@AddonReferenced
	public static CombinationRecipeManager getInstance() {
		return INSTANCE;
	}
	
	public void addRecipe(ItemStack output, long cost, Ingredient input, NonNullList<Ingredient> pedestals) {
		if (ModConfig.confCraftingCoreEnabled) {
			this.recipes.add(new CombinationRecipe(output, cost, input, pedestals));
		}
	}

	public void addRecipe(ItemStack output, long cost, int perTick, Ingredient input, NonNullList<Ingredient> pedestals) {
		if (ModConfig.confCraftingCoreEnabled) {
			this.recipes.add(new CombinationRecipe(output, cost, perTick, input, pedestals));
		}
	}

	@AddonReferenced
	public ArrayList<CombinationRecipe> getRecipes() {
		return this.recipes;
	}
	
	public void removeRecipes(ItemStack stack) {
		this.recipes.removeIf(o -> o.getOutput().isItemEqual(stack));
	}
}
