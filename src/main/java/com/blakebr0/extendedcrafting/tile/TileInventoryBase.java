package com.blakebr0.extendedcrafting.tile;

import com.blakebr0.cucumber.tile.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

// extending this sends a packet each time this is marked dirty, should that be considered excessive?
public abstract class TileInventoryBase extends TileEntityBase implements IInventory {

	private final String unlocalizedName;

	public TileInventoryBase(String unlocalizedName) {
		this.unlocalizedName = unlocalizedName;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public String getName() {
		return getDisplayName().getFormattedText();
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(String.format("tile.ec.%s.name", unlocalizedName));
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

}
