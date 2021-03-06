package com.blakebr0.extendedcrafting.client.gui;

import com.blakebr0.extendedcrafting.client.container.*;
import com.blakebr0.extendedcrafting.client.container.automationinterface.ContainerAutomationInterface;
import com.blakebr0.extendedcrafting.client.gui.automationinterface.GuiAutomationInterface;
import com.blakebr0.extendedcrafting.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int CRAFTING_CORE = 0;

	public static final int HANDHELD_TABLE = 4;

	public static final int AUTOMATION_INTERFACE = 6;

	public static final int BASIC_TABLE = 7;
	public static final int ADVANCED_TABLE = 8;
	public static final int ELITE_TABLE = 9;
	public static final int ULTIMATE_TABLE = 10;

	public static final int COMPRESSOR = 13;

	public static final int ENDER_CRAFTER = 19;

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if (tile == null && ID != HANDHELD_TABLE) {
			return null;
		}
		switch (ID) {
			case CRAFTING_CORE:
				return new GuiCraftingCore((TileCraftingCore) tile, new ContainerCraftingCore(player.inventory, (TileCraftingCore) tile));
			case HANDHELD_TABLE:
				return new GuiHandheldTable(new ContainerHandheldTable(player.inventory, world));
			case AUTOMATION_INTERFACE:
				return new GuiAutomationInterface(new ContainerAutomationInterface(player.inventory, (TileAutomationInterface) tile));
			case BASIC_TABLE:
				return new GuiBasicTable(new ContainerBasicTable(player.inventory, (TileBasicCraftingTable) tile));
			case ADVANCED_TABLE:
				return new GuiAdvancedTable(new ContainerAdvancedTable(player.inventory, (TileAdvancedCraftingTable) tile));
			case ELITE_TABLE:
				return new GuiEliteTable(new ContainerEliteTable(player.inventory, (TileEliteCraftingTable) tile));
			case ULTIMATE_TABLE:
				return new GuiUltimateTable(new ContainerUltimateTable(player.inventory, (TileUltimateCraftingTable) tile));
			case COMPRESSOR:
				return new GuiCompressor((TileCompressor) tile, new ContainerCompressor(player.inventory, (TileCompressor) tile));
			case ENDER_CRAFTER:
				return new GuiEnderCrafter((TileEnderCrafter) tile, new ContainerEnderCrafter(player.inventory, (TileEnderCrafter) tile));
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		if(tile == null && ID != HANDHELD_TABLE) {
			return null;
		}
		switch (ID) {
			case CRAFTING_CORE:
				return new ContainerCraftingCore(player.inventory, (TileCraftingCore) tile);
			case HANDHELD_TABLE:
				return new ContainerHandheldTable(player.inventory, world);
			case AUTOMATION_INTERFACE:
				return new ContainerAutomationInterface(player.inventory, (TileAutomationInterface) tile);
			case BASIC_TABLE:
				return new ContainerBasicTable(player.inventory, (TileBasicCraftingTable) tile);
			case ADVANCED_TABLE:
				return new ContainerAdvancedTable(player.inventory, (TileAdvancedCraftingTable) tile);
			case ELITE_TABLE:
				return new ContainerEliteTable(player.inventory, (TileEliteCraftingTable) tile);
			case ULTIMATE_TABLE:
				return new ContainerUltimateTable(player.inventory, (TileUltimateCraftingTable) tile);
			case COMPRESSOR:
				return new ContainerCompressor(player.inventory, (TileCompressor) tile);
			case ENDER_CRAFTER:
				return new ContainerEnderCrafter(player.inventory, (TileEnderCrafter) tile);
		}
		return null;
	}

}
