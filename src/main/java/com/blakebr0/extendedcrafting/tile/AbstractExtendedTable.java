package com.blakebr0.extendedcrafting.tile;

import com.blakebr0.extendedcrafting.lib.IExtendedTable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public abstract class AbstractExtendedTable extends TileInventoryBase implements IInventory, IExtendedTable {

	protected final int lineSize;
	protected NonNullList<ItemStack> matrix;
	protected ItemStack result = ItemStack.EMPTY;

	public AbstractExtendedTable(String unlocalizedName) {
		super(unlocalizedName);
		lineSize = 3;
		matrix = NonNullList.withSize(lineSize * lineSize, ItemStack.EMPTY);
	}

	public AbstractExtendedTable(int lineSize, String tier) {
		super("table_" + tier);
		matrix = NonNullList.withSize(lineSize * lineSize, ItemStack.EMPTY);
		this.lineSize = lineSize;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag = super.writeToNBT(tag);

		if (!this.result.isEmpty()) {
			tag.setTag("Result", this.result.serializeNBT());
		} else {
			tag.removeTag("Result");
		}

		tag.merge(ItemStackHelper.saveAllItems(tag, this.matrix));

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.result = new ItemStack(tag.getCompoundTag("Result"));
		ItemStackHelper.loadAllItems(tag, this.matrix);
	}

	@Override
	public ItemStack getResult() {
		return this.result;
	}

	@Override
	public void setResult(ItemStack result) {
		this.result = result;
		this.markDirty();
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.matrix.set(slot, stack);
	}

	@Override
	public NonNullList<ItemStack> getMatrix() {
		return this.matrix;
	}

	@Override
	public final int getLineSize() {
		return lineSize;
	}

	@Override
	public final int getSizeInventory() {
		return lineSize * lineSize;
	}

	@Override
	public boolean isEmpty() {
		return this.matrix.stream().allMatch(ItemStack::isEmpty) && this.result.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.matrix.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		return ItemStackHelper.getAndSplit(this.matrix, index, amount);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.matrix, index);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public void clear() {
		this.matrix = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		this.setResult(ItemStack.EMPTY);
	}

	@Override
	protected void dirtyPacket() {
	}
}
