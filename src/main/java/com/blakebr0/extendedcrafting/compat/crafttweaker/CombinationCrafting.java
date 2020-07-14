package com.blakebr0.extendedcrafting.compat.crafttweaker;

import com.blakebr0.extendedcrafting.crafting.CombinationRecipe;
import com.blakebr0.extendedcrafting.crafting.CombinationRecipeManager;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.extendedcrafting.CombinationCrafting")
public class CombinationCrafting {
	
	@ZenMethod
	public static void addRecipe(IItemStack output, long cost, IItemStack input, IIngredient[] ingredients) {
		CraftTweakerAPI.apply(new Add(new CombinationRecipe(CraftTweakerMC.getItemStack(output),
				cost,
				CraftTweakerUtils.toIngredient(input),
				CraftTweakerUtils.toIngredients(ingredients))));
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, long cost, int perTick, IItemStack input, IIngredient[] ingredients) {
		CraftTweakerAPI.apply(new Add(new CombinationRecipe(CraftTweakerMC.getItemStack(output),
				cost,
				perTick,
				CraftTweakerUtils.toIngredient(input),
				CraftTweakerUtils.toIngredients(ingredients))));
	}

	@ZenMethod
	public static void remove(IItemStack target) {
		CraftTweakerAPI.apply(new Remove(CraftTweakerMC.getItemStack(target)));
	}

	private static class Add implements IAction {
		CombinationRecipe recipe;

		public Add(CombinationRecipe add) {
			this.recipe = add;
		}

		@Override
		public void apply() {
			CombinationRecipeManager.getInstance().getRecipes().add(this.recipe);
		}

		@Override
		public String describe() {
			return "Adding a Combination Crafting recipe for " + this.recipe.getOutput().getDisplayName();
		}
	}

	private static class Remove implements IAction {
		ItemStack remove;

		public Remove(ItemStack remove) {
			this.remove = remove;
		}

		@Override
		public void apply() {
			CombinationRecipeManager.getInstance().removeRecipes(this.remove);
		}

		@Override
		public String describe() {
			return "Removing all Combination Crafting recipes for " + this.remove.getDisplayName();
		}
	}

}
