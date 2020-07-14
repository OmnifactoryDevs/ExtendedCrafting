package com.blakebr0.extendedcrafting.crafting.table;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
public class TableRecipeShaped extends TableRecipeBase {

	protected int width;
	protected int height;

	public TableRecipeShaped(int tier, ItemStack result, int width, int height, NonNullList<Ingredient> ingredients) {
		super(tier, result, ingredients);
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if (this.tier != 0 && this.tier != this.getTierFromGridSize(inv))
			return false;

		for (int x = 0; x <= inv.getWidth() - this.width; x++) {
			for (int y = 0; y <= inv.getHeight() - this.height; y++) {
				if (this.checkMatch(inv, x, y, false)) {
					return true;
				}

				if (this.mirrored && this.checkMatch(inv, x, y, true)) {
					return true;
				}
			}
		}

		return false;
	}

	protected boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror) {
		for (int x = 0; x < inv.getWidth(); x++) {
			for (int y = 0; y < inv.getHeight(); y++) {
				int subX = x - startX;
				int subY = y - startY;
				Ingredient target = Ingredient.EMPTY;

				if (subX >= 0 && subY >= 0 && subX < this.width && subY < this.height) {
					if (mirror) {
						target = this.input.get(this.width - subX - 1 + subY * this.width);
					} else {
						target = this.input.get(subX + subY * this.width);
					}
				}

				if (!target.apply(inv.getStackInRowAndColumn(x, y))) {
					return false;
				}
			}
		}

		return true;
	}
	
	@Override
	public boolean matches(IItemHandlerModifiable inv) {
		if (this.tier != 0 && this.tier != this.getTierFromSize(inv.getSlots()))
			return false;

		int size = (int) Math.sqrt(inv.getSlots());
		for (int x = 0; x <= size - this.width; x++) {
			for (int y = 0; y <= size - this.height; y++) {
				if (this.checkMatch(inv, x, y, false)) {
					return true;
				}

				if (this.mirrored && this.checkMatch(inv, x, y, true)) {
					return true;
				}
			}
		}

		return false;
	}
	
	protected boolean checkMatch(IItemHandlerModifiable inv, int startX, int startY, boolean mirror) {
		int size = (int) Math.sqrt(inv.getSlots());
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				int subX = x - startX;
				int subY = y - startY;
				Ingredient target = Ingredient.EMPTY;

				if (subX >= 0 && subY >= 0 && subX < this.width && subY < this.height) {
					if (mirror) {
						target = this.input.get(this.width - subX - 1 + subY * this.width);
					} else {
						target = this.input.get(subX + subY * this.width);
					}
				}

				if (!target.apply(inv.getStackInSlot(x + y * size))) {
					return false;
				}
			}
		}

		return true;
	}

	public TableRecipeShaped setMirrored(boolean mirror) {
		this.mirrored = mirror;
		return this;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= this.width && height >= this.height;
	}

	@Override
	public int getTier() {
		if (this.tier > 0) return this.tier;

		return this.width < 4 && this.height < 4 ? 1
				 : this.width < 6 && this.height < 6 ? 2
				 : this.width < 8 && this.height < 8 ? 3
				 : 4;
	}
	
	public TableRecipeShaped withTransformers(Map<Integer, Function<ItemStack, ItemStack>> transformers) {
		this.transformers = transformers;
		return this;
	}
}