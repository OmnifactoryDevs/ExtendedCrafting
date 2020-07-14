package com.blakebr0.extendedcrafting.crafting.table;

import com.blakebr0.cucumber.helper.RecipeHelper;
import com.blakebr0.extendedcrafting.config.ModConfig;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TableRecipeShapeless extends TableRecipeBase {

	protected ItemStack output;
	protected NonNullList<Ingredient> input = NonNullList.create();
	protected ResourceLocation group;
	protected int tier;
	protected Map<Integer, Function<ItemStack, ItemStack>> transformers;
	public int enderCrafterRecipeTimeRequired = ModConfig.confEnderTimeRequired;

	public TableRecipeShapeless(int tier, ItemStack result, NonNullList<Ingredient> recipe) {
		super(tier, result, recipe);
		this.group = RecipeHelper.EMPTY_GROUP;
		this.tier = tier;
		this.output = result.copy();
		for (Ingredient i : recipe) {
			if (i == null) {
				StringBuilder ret = new StringBuilder("Invalid shapeless ore recipe: ");
				for (Object tmp : recipe) {
					ret.append(tmp).append(", ");
				}

				ret.append(this.output);

				throw new IllegalArgumentException(ret.toString());
			}
			input.add(i);
		}
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		LinkedList<Ingredient> required = new LinkedList<>(input);

		if (this.tier != 0 && this.tier != this.getTierFromSize(inv.getSizeInventory()))
			return false;

		for (int x = 0; x < inv.getSizeInventory(); x++) {
			ItemStack slot = inv.getStackInSlot(x);

			if (!slot.isEmpty()) {
				boolean inRecipe = false;
				Iterator<Ingredient> req = required.iterator();

				while (req.hasNext()) {
					Ingredient target = req.next();
					if (target.apply(slot)) {
						inRecipe = true;
						req.remove();
						break;
					}
				}

				if (!inRecipe) return false;
			}
		}

		return required.isEmpty();
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= this.input.size();
	}

	@Override
	public int getTier() {
		if (this.tier > 0) return this.tier;
		return this.input.size() < 10 ? 1
				: this.input.size() < 26 ? 2
				: this.input.size() < 50 ? 3
				: 4;
	}

	@Override
	public boolean matches(IItemHandlerModifiable grid) {
		NonNullList<Ingredient> required = NonNullList.create();
		required.addAll(this.input);

		if (this.tier != 0 && this.tier != this.getTierFromSize(grid.getSlots()))
			return false;

		for (int x = 0; x < grid.getSlots(); x++) {
			ItemStack slot = grid.getStackInSlot(x);

			if (!slot.isEmpty()) {
				boolean inRecipe = false;
				Iterator<Ingredient> req = required.iterator();

				while (req.hasNext()) {
					if (req.next().apply(slot)) {
						inRecipe = true;
						req.remove();
						break;
					}
				}

				if (!inRecipe) return false;
			}
		}

		return required.isEmpty();
	}

	public TableRecipeShapeless withTransforms(Map<Integer, Function<ItemStack, ItemStack>> transformers) {
		this.transformers = transformers;
		return this;
	}
}