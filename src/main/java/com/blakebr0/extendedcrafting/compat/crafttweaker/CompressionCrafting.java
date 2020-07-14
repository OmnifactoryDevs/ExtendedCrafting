package com.blakebr0.extendedcrafting.compat.crafttweaker;

import com.blakebr0.extendedcrafting.crafting.CompressorRecipe;
import com.blakebr0.extendedcrafting.crafting.CompressorRecipeManager;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.extendedcrafting.CompressionCrafting")
public class CompressionCrafting {

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input, int inputCount, IIngredient catalyst, int powerCost) {
		CraftTweakerAPI.apply(new Add(new CompressorRecipe(CraftTweakerMC.getItemStack(output), CraftTweakerUtils.toIngredient(input), inputCount, CraftTweakerUtils.toIngredient(catalyst), false, powerCost)));
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IIngredient input, int inputCount, IIngredient catalyst, int powerCost, int powerRate) {
		CraftTweakerAPI.apply(new Add(new CompressorRecipe(CraftTweakerMC.getItemStack(output), CraftTweakerUtils.toIngredient(input), inputCount, CraftTweakerUtils.toIngredient(catalyst), false, powerCost, powerRate)));
	}

	@ZenMethod
	public static void remove(IItemStack target) {
		CraftTweakerAPI.apply(new Remove(CraftTweakerMC.getItemStack(target)));
	}

	private static class Add implements IAction {
		CompressorRecipe recipe;

		public Add(CompressorRecipe add) {
			this.recipe = add;
		}

		@Override
		public void apply() {
			CompressorRecipeManager.getInstance().getRecipes().add(this.recipe);
		}

		@Override
		public String describe() {
			return "Adding a Compression Crafting recipe for " + this.recipe.getOutput().getDisplayName();
		}
	}

	private static class Remove implements IAction {
		ItemStack remove;

		public Remove(ItemStack remove) {
			this.remove = remove;
		}

		@Override
		public void apply() {
			CompressorRecipeManager.getInstance().removeRecipes(this.remove);
		}

		@Override
		public String describe() {
			return "Removing all Compression Crafting recipes for " + this.remove.getDisplayName();
		}
	}
}