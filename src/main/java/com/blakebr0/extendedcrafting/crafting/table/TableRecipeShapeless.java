package com.blakebr0.extendedcrafting.crafting.table;

import com.blakebr0.cucumber.helper.RecipeHelper;
import com.blakebr0.extendedcrafting.config.ModConfig;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

public class TableRecipeShapeless extends TableRecipeBase {

	protected final ItemStack output;
	protected final NonNullList<Ingredient> input = NonNullList.create();
	protected final ResourceLocation group;
	protected final int tier;
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

			mark:
			{
				if (!slot.isEmpty()) {
					Iterator<Ingredient> req = required.iterator();

					while (req.hasNext()) {
						Ingredient target = req.next();
						if (target.apply(slot)) {
							req.remove();
							break mark;
						}
					}
					return false;
				}
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

	public TableRecipeShapeless withTransforms(Map<Integer, Function<ItemStack, ItemStack>> transformers) {
		this.transformers = transformers;
		return this;
	}
}
