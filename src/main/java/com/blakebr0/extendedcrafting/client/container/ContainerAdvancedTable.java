package com.blakebr0.extendedcrafting.client.container;

import com.blakebr0.extendedcrafting.tile.TileAdvancedCraftingTable;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerAdvancedTable extends AbstractTableContainer {

	public static final int X_SIZE = 176;
	public static final int Y_SIZE = 206;
	public static final int GRID_START_X = 14;
	public static final int OUTPUT_SLOT_X = 142;

	public ContainerAdvancedTable(InventoryPlayer player, TileAdvancedCraftingTable tile) {
		super(player,
				tile,
				X_SIZE,
				Y_SIZE,
				GRID_START_X,
				OUTPUT_SLOT_X);
	}

}
