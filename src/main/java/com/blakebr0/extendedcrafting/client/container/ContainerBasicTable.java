package com.blakebr0.extendedcrafting.client.container;

import com.blakebr0.extendedcrafting.tile.TileBasicCraftingTable;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerBasicTable extends AbstractTableContainer {

	public static final int X_SIZE = 176;
	public static final int Y_SIZE = 170;
	public static final int GRID_START_X = 32;
	public static final int OUTPUT_SLOT_X = 124;

	public ContainerBasicTable(InventoryPlayer player, TileBasicCraftingTable tile) {
		super(player,
				tile,
				X_SIZE,
				Y_SIZE,
				GRID_START_X,
				OUTPUT_SLOT_X);
	}

}
