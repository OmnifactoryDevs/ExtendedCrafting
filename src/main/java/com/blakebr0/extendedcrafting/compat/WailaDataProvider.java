package com.blakebr0.extendedcrafting.compat;

import com.blakebr0.cucumber.util.Utils;
import com.blakebr0.extendedcrafting.block.*;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockAdvancedTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockBasicTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockEliteTable;
import com.blakebr0.extendedcrafting.block.craftingtable.BlockUltimateTable;
import com.blakebr0.extendedcrafting.config.ModConfig;
import com.blakebr0.extendedcrafting.crafting.CombinationRecipe;
import com.blakebr0.extendedcrafting.crafting.CompressorRecipe;
import com.blakebr0.extendedcrafting.tile.*;
import mcp.MethodsReturnNonnullByDefault;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class WailaDataProvider implements IWailaDataProvider {

	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new WailaDataProvider(), BlockLamp.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockTrimmed.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockBasicTable.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockAdvancedTable.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockEliteTable.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockUltimateTable.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockPedestal.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockCraftingCore.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockAutomationInterface.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockEnderCrafter.class);
		registrar.registerBodyProvider(new WailaDataProvider(), BlockCompressor.class);
	}

	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor data, IWailaConfigHandler config) {
		Block block = data.getBlock();
		TileEntity tile = data.getTileEntity();
		
		if (block instanceof BlockLamp) {
			tooltip.add(Utils.localize("tooltip.ec.lamp_" + BlockLamp.Type.byMetadata(data.getMetadata()).getName()));
		}
		
		if (block instanceof BlockTrimmed) {
			tooltip.add(Utils.localize("tooltip.ec.trimmed_" + BlockTrimmed.Type.byMetadata(data.getMetadata()).getName()));
		}
		
		if (block instanceof BlockPedestal && tile instanceof TilePedestal && !tile.isInvalid()) {
			TilePedestal pedestal = (TilePedestal) tile;
			ItemStack result = pedestal.getStack();
			if (!result.isEmpty()) {
				tooltip.add(result.getDisplayName());
			}
		}
		
		if (block instanceof BlockCraftingCore && tile instanceof TileCraftingCore && !tile.isInvalid()) {
			TileCraftingCore core = (TileCraftingCore) tile;
			
			if (ModConfig.confEnergyInWaila) {
				tooltip.add(Utils.format(core.getEnergy().getEnergyStored()) + " FE");
			}
			
			CombinationRecipe recipe = core.getRecipe();
			ItemStack output = recipe == null ? ItemStack.EMPTY : recipe.getOutput();
			tooltip.add(Utils.localize("tooltip.ec.crafting", output.getCount(), output.getDisplayName()));
		}
		
		if (block instanceof BlockBasicTable) tooltip.add(Utils.localize("tooltip.ec.tier", 1));
		if (block instanceof BlockAdvancedTable) tooltip.add(Utils.localize("tooltip.ec.tier", 2));
		if (block instanceof BlockEliteTable) tooltip.add(Utils.localize("tooltip.ec.tier", 3));
		if (block instanceof BlockUltimateTable) tooltip.add(Utils.localize("tooltip.ec.tier", 4));
		
		if (block instanceof BlockAutomationInterface && tile instanceof TileAutomationInterface && !tile.isInvalid()) {
			TileAutomationInterface auto = (TileAutomationInterface) tile;
			
			if (ModConfig.confEnergyInWaila) {
				tooltip.add(Utils.format(auto.getEnergy().getEnergyStored()) + " FE");
			}
			
			ItemStack result = auto.getResult();
			if (!result.isEmpty()) {
				tooltip.add(Utils.localize("tooltip.ec.crafting", result.getCount(), result.getDisplayName()));
			}
		}
		
		if (block instanceof BlockEnderCrafter && tile instanceof TileEnderCrafter && !tile.isInvalid()) {
			TileEnderCrafter crafter = (TileEnderCrafter) tile;
			ItemStack result = crafter.getResult();
			if (!result.isEmpty()) {
				tooltip.add(Utils.localize("tooltip.ec.output", result.getCount(), result.getDisplayName()));
			}
		}
		
		if (block instanceof BlockCompressor && tile instanceof TileCompressor && !tile.isInvalid()) {
			TileCompressor compressor = (TileCompressor) tile;
			
			if (ModConfig.confEnergyInWaila) {
				tooltip.add(Utils.format(compressor.getEnergy().getEnergyStored()) + " FE");
			}
			
			CompressorRecipe recipe = compressor.getRecipe();
			ItemStack output = recipe == null ? ItemStack.EMPTY : recipe.getOutput();
			tooltip.add(Utils.localize("tooltip.ec.crafting", output.getCount(), output.getDisplayName()));
		}
		
		return tooltip;
	}

}
