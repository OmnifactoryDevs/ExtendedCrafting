package com.blakebr0.extendedcrafting.tile;

import com.blakebr0.extendedcrafting.lib.IExtendedTable;
import net.minecraft.inventory.IInventory;

public class TileAdvancedCraftingTable extends AbstractExtendedTable implements IInventory, IExtendedTable {

	public TileAdvancedCraftingTable() {
		super(5, "advanced");
	}

}
