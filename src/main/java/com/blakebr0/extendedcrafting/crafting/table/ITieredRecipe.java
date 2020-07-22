package com.blakebr0.extendedcrafting.crafting.table;

import net.minecraft.item.crafting.IRecipe;

public interface ITieredRecipe extends IRecipe {
	int getTier();
}
