package com.blakebr0.extendedcrafting.tile;

import com.blakebr0.extendedcrafting.lib.IExtendedTable;
import net.minecraft.inventory.IInventory;

public class TileBasicCraftingTable extends AbstractExtendedTable implements IInventory, IExtendedTable {

	public TileBasicCraftingTable() {
		super(9, "basic");
	}

}
