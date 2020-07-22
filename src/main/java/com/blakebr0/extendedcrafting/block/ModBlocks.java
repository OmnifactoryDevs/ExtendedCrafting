package com.blakebr0.extendedcrafting.block;

import com.blakebr0.cucumber.registry.ModRegistry;
import com.blakebr0.cucumber.registry.Ore;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockAdvancedTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockBasicTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockEliteTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockUltimateTable;

import com.blakebr0.extendedcrafting.lib.AddonReferenced;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

@AddonReferenced
public class ModBlocks {

	public static final BlockStorage blockStorage = new BlockStorage();
	public static final BlockFrame blockFrame = new BlockFrame();
	public static final BlockLamp blockLamp = new BlockLamp();
	public static final BlockTrimmed blockTrimmed = new BlockTrimmed();

	public static final BlockPedestal blockPedestal = new BlockPedestal();
	@AddonReferenced public static final BlockCraftingCore blockCraftingCore = new BlockCraftingCore();
	
	public static final BlockBasic blockCraftingTable = new BlockBasic("ec.crafting_table", Material.WOOD, SoundType.WOOD, 2.5F, 10.0F);
	
	public static final BlockAutomationInterface blockAutomationInterface = new BlockAutomationInterface();

	@AddonReferenced public static final BlockBasicTable blockBasicTable = new BlockBasicTable();
	@AddonReferenced public static final BlockAdvancedTable blockAdvancedTable = new BlockAdvancedTable();
	@AddonReferenced public static final BlockEliteTable blockEliteTable = new BlockEliteTable();
	@AddonReferenced public static final BlockUltimateTable blockUltimateTable = new BlockUltimateTable();

	public static final BlockCompressor blockCompressor = new BlockCompressor();
	
	public static final BlockEnderAlternator blockEnderAlternator = new BlockEnderAlternator();
	@AddonReferenced public static final BlockEnderCrafter blockEnderCrafter = new BlockEnderCrafter();

	public static void init() {
		final ModRegistry registry = ExtendedCrafting.REGISTRY;

		registry.register(blockStorage, "storage", new ItemBlockStorage(blockStorage), 
				Ore.of(0, "blockBlackIron"), Ore.of(1, "blockLuminessence"), Ore.of(2, "blockNetherStar"),
				Ore.of(3, "blockCrystaltine"), Ore.of(4, "blockUltimate"));
		registry.register(blockFrame, "frame");
		registry.register(blockLamp, "lamp", new ItemBlockLamp(blockLamp));
		registry.register(blockTrimmed, "trimmed", new ItemBlockTrimmed(blockTrimmed));
		
		registry.register(blockCraftingTable, "crafting_table");

		registry.register(blockPedestal, "pedestal");
		registry.register(blockCraftingCore, "crafting_core");

		registry.register(blockAutomationInterface, "interface");
		
		registry.register(blockBasicTable, "table_basic");
		registry.register(blockAdvancedTable, "table_advanced");
		registry.register(blockEliteTable, "table_elite");
		registry.register(blockUltimateTable, "table_ultimate");

		registry.register(blockCompressor, "compressor");
		
		registry.register(blockEnderAlternator, "ender_alternator");
		registry.register(blockEnderCrafter, "ender_crafter");
	}
}
