package com.blakebr0.extendedcrafting.block.craftingtable;

import com.blakebr0.cucumber.block.BlockBase;
import com.blakebr0.cucumber.iface.IEnableable;
import com.blakebr0.cucumber.util.Utils;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.client.gui.GuiHandler;
import com.blakebr0.extendedcrafting.config.ModConfig;
import com.blakebr0.extendedcrafting.tile.*;
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
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public abstract class AbstractTableBlock extends BlockBase implements ITileEntityProvider, IEnableable {
	private final TableTier tier;

	protected enum TableTier {
		BASIC(TileBasicCraftingTable::new),
		ADVANCED(TileAdvancedCraftingTable::new),
		ELITE(TileEliteCraftingTable::new),
		ULTIMATE(TileUltimateCraftingTable::new);

		public final Supplier<? extends AbstractExtendedTable> constructor;

		TableTier(Supplier<? extends AbstractExtendedTable> constructor) {
			this.constructor = constructor;
		}
	}

	public AbstractTableBlock(TableTier tier) {
		super("ec.table_" + tier.name().toLowerCase(), Material.IRON, SoundType.METAL, 5.0F, 10.0F);
		this.tier = tier;
		setCreativeTab(ExtendedCrafting.CREATIVE_TAB);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileAdvancedCraftingTable) {
				player.openGui(ExtendedCrafting.instance, GuiHandler.BASIC_TABLE + tier.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
			}

		}
		return true;
	}

	@Nullable
	protected AbstractExtendedTable getTile(World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);

		if(tile instanceof AbstractExtendedTable) {
			return (AbstractExtendedTable) tile;
		}
		return null;
	}

	@Nullable
	public AbstractExtendedTable createNewTileEntity(World worldIn, int meta) {
		return tier.constructor.get();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		AbstractExtendedTable tile = getTile(world, pos);
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
	public boolean isEnabled() {
		return ModConfig.confTableEnabled;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(Utils.localize("tooltip.ec.tier", tier.ordinal() + 1));
	}
}
