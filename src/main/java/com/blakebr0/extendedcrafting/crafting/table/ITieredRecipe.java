package com.blakebr0.extendedcrafting.crafting.table;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface ITieredRecipe extends IRecipe {

	int getTier();
	boolean matches(IItemHandlerModifiable grid);
}
