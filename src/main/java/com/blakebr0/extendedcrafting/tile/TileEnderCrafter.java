package com.blakebr0.extendedcrafting.tile;

import com.blakebr0.cucumber.helper.StackHelper;
import com.blakebr0.extendedcrafting.block.BlockEnderAlternator;
import com.blakebr0.extendedcrafting.config.ModConfig;
import com.blakebr0.extendedcrafting.crafting.endercrafter.EnderCrafterRecipeManager;
import com.blakebr0.extendedcrafting.crafting.endercrafter.IEnderCraftingRecipe;
import com.blakebr0.extendedcrafting.crafting.table.TableCrafting;
import com.blakebr0.extendedcrafting.lib.EmptyContainer;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileEnderCrafter extends AbstractExtendedTable implements ITickable {

	private int progress;
	private int progressReq;
	private TableCrafting tableCrafting;

	public TileEnderCrafter() {
		super("ender_crafter");
		tableCrafting = new TableCrafting(new EmptyContainer(), this);
	}

	@Override
	public void update() {
		if (!this.getWorld().isRemote) {
			TableCrafting crafting = tableCrafting;
			IEnderCraftingRecipe recipe = EnderCrafterRecipeManager.getInstance().findMatchingRecipe(crafting, this.getWorld());
			ItemStack result = recipe == null ? ItemStack.EMPTY : recipe.getCraftingResult(crafting);
			ItemStack output = this.getResult();
			if (!result.isEmpty() && (output.isEmpty() || StackHelper.canCombineStacks(output, result))) {
				List<BlockPos> alternators = this.getAlternatorPositions();
				int alternatorCount = alternators.size();

				if (alternatorCount > 0 && recipe != null) {
					this.progress(alternatorCount, recipe.getEnderCrafterTimeSeconds());
					
					for (BlockPos pos : alternators) {
						if (this.getWorld().isAirBlock(pos.up())) {
							((WorldServer) this.getWorld()).spawnParticle(EnumParticleTypes.PORTAL, false, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 1, 0, 0, 0, 0.1D);
						}
					}
					
					if (this.progress >= this.progressReq) {
						for (int i = 0; i < this.matrix.size(); i++) {
							this.decrStackSize(i, 1);
						}
						
						this.updateResult(result);
						this.progress = 0;
					}
					
					this.markDirty();
				}
			} else {
				if (this.progress > 0 || this.progressReq > 0) {
					this.progress = 0;
					this.progressReq = 0;
					this.markDirty();
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag = super.writeToNBT(tag);
		
		tag.setInteger("Progress", this.progress);
		tag.setInteger("ProgressReq", this.progressReq);
		
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.progress = tag.getInteger("Progress");
		this.progressReq = tag.getInteger("ProgressReq");
	}
	
	public void updateResult(ItemStack result) {
		if (this.result.isEmpty()) {
			this.setResult(result);
		} else {
			this.result.grow(result.getCount());
		}
	}
	
	public List<BlockPos> getAlternatorPositions() {
		ArrayList<BlockPos> alternators = new ArrayList<>();
		Iterable<BlockPos> blocks = BlockPos.getAllInBox(this.getPos().add(-3, -3, -3), this.getPos().add(3, 3, 3));
		for (BlockPos aoePos : blocks) {
			Block block = this.getWorld().getBlockState(aoePos).getBlock();
			if (block instanceof BlockEnderAlternator) {
				alternators.add(aoePos);
			}
		}
		
		return alternators;
	}
	
	public int getProgress() {
		return this.progress;
	}
	
	private void progress(int alternators, int timeRequired) {
		this.progress++;
		
		int timeReq = 20 * timeRequired;
		this.progressReq = (int) Math.max(timeReq - (timeReq * (ModConfig.confEnderAlternatorEff * alternators)), 20);
	}

}
