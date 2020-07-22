package com.blakebr0.extendedcrafting.client.container;

import com.blakebr0.extendedcrafting.tile.TileEliteCraftingTable;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerEliteTable extends AbstractTableContainer {

	public static final int X_SIZE = 200;
	public static final int Y_SIZE = 242;
	public static final int GRID_START_X = 8;
	public static final int OUTPUT_SLOT_X = 172;

	public ContainerEliteTable(InventoryPlayer player, TileEliteCraftingTable tile) {
		super(player,
				tile,
				X_SIZE,
				Y_SIZE,
				GRID_START_X,
				OUTPUT_SLOT_X);
	}

}
