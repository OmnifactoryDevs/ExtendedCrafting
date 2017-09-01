package com.blakebr0.extendedcrafting.block;

import com.blakebr0.cucumber.registry.ModRegistry;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockAdvancedTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockBasicTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockEliteTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockUltimateTable;

public class ModBlocks {

	public static BlockStorage blockStorage = new BlockStorage();

	public static BlockCraftingTable blockCraftingTable = new BlockCraftingTable();
	public static BlockFrame blockFrame = new BlockFrame();

	public static BlockPedestal blockPedestal = new BlockPedestal();
	public static BlockCraftingCore blockCraftingCore = new BlockCraftingCore();

	public static BlockBasicTable blockBasicTable = new BlockBasicTable();
	public static BlockAdvancedTable blockAdvancedTable = new BlockAdvancedTable();
	public static BlockEliteTable blockEliteTable = new BlockEliteTable();
	public static BlockUltimateTable blockUltimateTable = new BlockUltimateTable();

	public static BlockCompressor blockCompressor = new BlockCompressor();

	public static void init() {
		final ModRegistry registry = ExtendedCrafting.REGISTRY;

		registry.register(blockStorage, "storage", new ItemBlockStorage(blockStorage));
		registry.register(blockFrame, "frame");

		registry.register(blockCraftingTable, "crafting_table");

		registry.register(blockPedestal, "pedestal");
		registry.register(blockCraftingCore, "crafting_core");

		registry.register(blockBasicTable, "table_basic");
		registry.register(blockAdvancedTable, "table_advanced");
		registry.register(blockEliteTable, "table_elite");
		registry.register(blockUltimateTable, "table_ultimate");

		registry.register(blockCompressor, "compressor");
	}
}