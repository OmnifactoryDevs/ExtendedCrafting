package com.blakebr0.extendedcrafting.crafting.table;

import com.blakebr0.extendedcrafting.config.ModConfig;
import com.blakebr0.extendedcrafting.lib.AddonReferenced;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@AddonReferenced
public class TableRecipeManager {

	private static final TableRecipeManager INSTANCE = new TableRecipeManager();
	private final List<ITieredRecipe> recipes = new ArrayList<>();

	@AddonReferenced
	public static TableRecipeManager getInstance() {
		return INSTANCE;
	}

	public void addShaped(ItemStack result, CraftingHelper.ShapedPrimer primer) {
		addShaped(0, result, primer.width, primer.height, primer.input);
	}

	public void addShaped(int tier, ItemStack result, CraftingHelper.ShapedPrimer primer) {
		addShaped(tier, result, primer.width, primer.height, primer.input);
	}

	public void addShaped(int tier, ItemStack result, int width, int height, NonNullList<Ingredient> recipe) {
		TableRecipeShaped craft = new TableRecipeShaped(tier, result, width, height, recipe);

		if (ModConfig.confTableEnabled) {
			this.recipes.add(craft);
		}
	}

	public void addShapeless(ItemStack result, NonNullList<Ingredient> ingredients) {
		addShapeless(0, result, ingredients);
	}

	public void addShapeless(int tier, ItemStack result, NonNullList<Ingredient> ingredients) {
		TableRecipeShapeless recipe = new TableRecipeShapeless(tier, result, ingredients);

		if (ModConfig.confTableEnabled) {
			this.recipes.add(recipe);
		}
	}

	public ItemStack findMatchingRecipe(InventoryCrafting grid, World world) {
		int i = 0;
		ItemStack stack = ItemStack.EMPTY;
		ItemStack stack1 = ItemStack.EMPTY;

		for (int j = 0; j < grid.getSizeInventory(); j++) {
			ItemStack stack2 = grid.getStackInSlot(j);
			if (!stack2.isEmpty()) {
				if (i == 0) {
					stack = stack2;
				}

				if (i == 1) {
					stack1 = stack2;
				}

				i++;
			}
		}

		Item item = stack.getItem();
		if (i == 2 && item == stack1.getItem() && stack.getCount() == 1 && stack1.getCount() == 1 && item.isRepairable()) {
			int maxDamage = stack.getMaxDamage();
			int j1 = maxDamage - stack.getItemDamage();
			int k = maxDamage - stack1.getItemDamage();
			int l = j1 + k + maxDamage * 5 / 100;
			int i1 = maxDamage - l;

			if (i1 < 0) {
				i1 = 0;
			}

			return new ItemStack(item, 1, i1);
		} else {
			for (IRecipe recipe : this.recipes) {
				if (recipe.matches(grid, world)) {
					return recipe.getCraftingResult(grid);
				}
			}

			if (ModConfig.confTableUseRecipes && grid.getWidth() == 3 && grid.getHeight() == 3) {
				for (IRecipe recipe : ForgeRegistries.RECIPES.getValuesCollection()) {
					if (recipe.matches(grid, world)) {
						return recipe.getCraftingResult(grid);
					}
				}
			}

			return ItemStack.EMPTY;
		}
	}

	public List<ITieredRecipe> getRecipes() {
		return this.recipes;
	}

	public void removeRecipes(ItemStack stack) {
		this.recipes.removeIf(recipe -> recipe != null && recipe.getRecipeOutput().isItemEqual(stack));
	}

	@AddonReferenced
	public List<IRecipe> getRecipes(int size) {
		List<IRecipe> recipes = new ArrayList<>();
		for (IRecipe r : this.getRecipes()) {
			if (r.canFit(size, size)) {
				recipes.add(r);
			}
		}

		return recipes;
	}

	/**
	 * Gets all the recipes for the specified tier Basic is tier 1, Advanced
	 * tier 2, etc
	 *
	 * @param tier the tier of the recipe
	 * @return a list of recipes for this tier
	 */
	public List<ITieredRecipe> getRecipesTiered(int tier) {
		List<ITieredRecipe> recipes = new ArrayList<>();
		for (ITieredRecipe r : this.getRecipes()) {
			if (r != null) {
				if (r.getTier() == tier) {
					recipes.add(r);
				}
			}
		}

		return recipes;
	}

	public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting grid, World world) {
		for (int j = 0; j < getInstance().recipes.size(); j++) {
			IRecipe recipe = getInstance().recipes.get(j);
			if (recipe.matches(grid, world)) {
				return recipe.getRemainingItems(grid);
			}
		}

		return CraftingManager.getRemainingItems(grid, world);
	}
}

