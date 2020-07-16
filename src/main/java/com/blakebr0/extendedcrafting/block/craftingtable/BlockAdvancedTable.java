package com.blakebr0.extendedcrafting.block.craftingtable;

import com.blakebr0.cucumber.block.BlockBase;
import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.util.Utils;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.client.gui.GuiHandler;
import com.blakebr0.extendedcrafting.config.ModConfig;
import com.blakebr0.extendedcrafting.tile.TileAdvancedCraftingTable;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockAdvancedTable extends BlockBase implements ITileEntityProvider, IEnableable {

	public BlockAdvancedTable() {
		super("ec.table_advanced", Material.IRON, SoundType.METAL, 5.0F, 10.0F);
		this.setCreativeTab(ExtendedCrafting.CREATIVE_TAB);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileAdvancedCraftingTable();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileAdvancedCraftingTable) {
				player.openGui(ExtendedCrafting.instance, GuiHandler.ADVANCED_TABLE, world, pos.getX(), pos.getY(), pos.getZ());
			}

		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileAdvancedCraftingTable tile = (TileAdvancedCraftingTable) world.getTileEntity(pos);
		if (tile != null) {
			NonNullList<ItemStack> matrix = tile.getMatrix();
			for (ItemStack stack : matrix) {
				spawnAsEntity(world, pos, stack);
			}
		}
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing) {
		return facing == EnumFacing.UP ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(Utils.localize("tooltip.ec.tier", 2));
	}

	@Override
	public boolean isEnabled() {
		return ModConfig.confTableEnabled;
	}
}