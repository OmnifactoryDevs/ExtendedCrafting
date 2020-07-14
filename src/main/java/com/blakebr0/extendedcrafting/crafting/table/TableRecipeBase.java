package com.blakebr0.extendedcrafting.crafting.table;

import com.blakebr0.cucumber.helper.RecipeHelper;
import com.blakebr0.extendedcrafting.config.ModConfig;
import com.blakebr0.extendedcrafting.crafting.endercrafter.IEnderCraftingRecipe;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
public abstract class TableRecipeBase implements ITieredRecipe, IEnderCraftingRecipe {

	public static final int MAX_CRAFT_GRID_WIDTH = 9;
	public static final int MAX_CRAFT_GRID_HEIGHT = 9;

	protected final ItemStack output;
	protected final NonNullList<Ingredient> input;
	protected boolean mirrored = true;
	protected final ResourceLocation group;
	protected final int tier;
	protected Map<Integer, Function<ItemStack, ItemStack>> transformers;
	public int enderCrafterRecipeTimeRequired = ModConfig.confEnderTimeRequired;

	public TableRecipeBase(int tier, ItemStack result, NonNullList<Ingredient> ingredients) {
		this.group = RecipeHelper.EMPTY_GROUP;
		this.output = result.copy();
		this.input = ingredients;
		this.tier = tier;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		return this.output.copy();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.input;
	}

	@Override
	public String getGroup() {
		return this.group == null ? "" : this.group.toString();
	}

	protected int getTierFromSize(int size) {
		return size < 10 ? 1
				: size < 26 ? 2
				: size < 50 ? 3
				: 4;
	}

	protected int getTierFromGridSize(InventoryCrafting inv) {
		int size = inv.getSizeInventory();
		return size < 10 ? 1
				: size < 26 ? 2
				: size < 50 ? 3
				: 4;
	}

	public boolean requiresTier() {
		return this.tier > 0;
	}

	@Override
	public int getEnderCrafterTimeSeconds() {
		return this.enderCrafterRecipeTimeRequired;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> remaining = ITieredRecipe.super.getRemainingItems(inv);
		if (this.transformers != null && !this.transformers.isEmpty()) {
			this.transformers.forEach((i, transformer) ->
					remaining.set(i, transformer.apply(inv.getStackInSlot(i))));
		}

		return remaining;
	}

	private ResourceLocation registryName;

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		registryName = name;
		return this;
	}

	@Nullable
	@Override
	public ResourceLocation getRegistryName() {
		return registryName;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return IRecipe.class;
	}

}
