package com.blakebr0.extendedcrafting.client.container;

import com.blakebr0.extendedcrafting.tile.TileUltimateCraftingTable;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerUltimateTable extends AbstractTableContainer {

	public static final int X_SIZE = 234;
	public static final int Y_SIZE = 278;
	public static final int GRID_START_X = 8;
	public static final int OUTPUT_SLOT_X = 206;

	public ContainerUltimateTable(InventoryPlayer player, TileUltimateCraftingTable tile) {
		super(player,
				tile,
				X_SIZE,
				Y_SIZE,
				GRID_START_X,
				OUTPUT_SLOT_X);
	}

}
