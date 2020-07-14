package com.blakebr0.extendedcrafting.crafting.table;

import com.blakebr0.extendedcrafting.config.ModConfig;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class TableRecipeManager {

	private static final TableRecipeManager INSTANCE = new TableRecipeManager();
	private List<ITieredRecipe> recipes = new ArrayList<>();

	public static TableRecipeManager getInstance() {
		return INSTANCE;
	}

	public TableRecipeShaped addShaped(ItemStack result, Object... recipe) {
		CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(recipe);
		return addShaped(0, result, primer.width, primer.height, primer.input);
	}

	public TableRecipeShaped addShaped(int tier, ItemStack result, int width, int height, NonNullList<Ingredient> recipe) {
		TableRecipeShaped craft = new TableRecipeShaped(tier, result, width, height, recipe);
		
		if (ModConfig.confTableEnabled) {
			this.recipes.add(craft);
		}
		
		return craft;
	}

	public TableRecipeShapeless addShapeless(ItemStack result, NonNullList<Ingredient> ingredients) {
		return addShapeless(0, result, ingredients);
	}

	public TableRecipeShapeless addShapeless(int tier, ItemStack result, NonNullList<Ingredient> ingredients) {
		TableRecipeShapeless recipe = new TableRecipeShapeless(tier, result, ingredients);

		if (ModConfig.confTableEnabled) {
			this.recipes.add(recipe);
		}

		return recipe;
	}

	@Deprecated
	public TableRecipeShapeless addShapeless(ItemStack result, Object... ingredients) {
		NonNullList<Ingredient> ing = Arrays.stream(ingredients)
				.map(CraftingHelper::getIngredient)
				.collect(Collectors.toCollection(NonNullList::create));
		return addShapeless(0, result, ing);
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

		if (i == 2 && stack.getItem() == stack1.getItem() && stack.getCount() == 1 && stack1.getCount() == 1 && stack.getItem().isRepairable()) {
			Item item = stack.getItem();
			int maxDamage = stack.getMaxDamage();
			int j1 = maxDamage - stack.getItemDamage();
			int k = maxDamage - stack1.getItemDamage();
			int l = j1 + k + maxDamage * 5 / 100;
			int i1 = maxDamage - l;

			if (i1 < 0) {
				i1 = 0;
			}
			
			return new ItemStack(stack.getItem(), 1, i1);
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
