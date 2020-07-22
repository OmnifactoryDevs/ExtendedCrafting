package com.blakebr0.extendedcrafting.client.container;

import com.blakebr0.extendedcrafting.crafting.table.TableCraftResult;
import com.blakebr0.extendedcrafting.crafting.table.TableCrafting;
import com.blakebr0.extendedcrafting.crafting.table.TableRecipeManager;
import com.blakebr0.extendedcrafting.crafting.table.TableResultHandler;
import com.blakebr0.extendedcrafting.tile.AbstractExtendedTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AbstractTableContainer extends Container {
	public static final int SLOT_SIZE = 18;
	public static final int PLAYER_INV_WIDTH = 9;
	public static final int PLAYER_INV_ROWS = 3;
	public static final int GRID_START_Y = 18;
	public final InventoryCrafting matrix;
	public final IInventory result;
	public final AbstractExtendedTable tile;

	public AbstractTableContainer(InventoryPlayer player, AbstractExtendedTable tile, int guiWidth, int guiHeight, int gridStartX, int outputSlotX) {
		this.matrix = new TableCrafting(this, tile);
		this.result = new TableCraftResult(tile);
		this.tile = tile;
		int lineSize = tile.getLineSize();

		int outputSlotY = GRID_START_Y + ((lineSize - 1) * SLOT_SIZE) / 2;
		this.addSlotToContainer(new TableResultHandler(this.matrix, this.result, tile.getWorld(), 0, outputSlotX, outputSlotY));

		int y;
		int x;
		for (y = 0; y < lineSize; y++) {
			for (x = 0; x < lineSize; x++) {
				this.addSlotToContainer(new Slot(this.matrix, x + y * lineSize, gridStartX + x * SLOT_SIZE, GRID_START_Y + y * SLOT_SIZE));
			}
		}

		// player inventory

		int hotbarStartY = guiHeight - SLOT_SIZE - 6;
		int playerInvStartX = getPlayerInvStartX(guiWidth);
		int playerInvStartY = hotbarStartY - SLOT_SIZE * PLAYER_INV_ROWS - 4;

		for (y = 0; y < PLAYER_INV_ROWS; y++) {
			for (x = 0; x < PLAYER_INV_WIDTH; x++) {
				this.addSlotToContainer(new Slot(player, x + (y + 1) * PLAYER_INV_WIDTH, playerInvStartX + x * SLOT_SIZE, playerInvStartY + y * SLOT_SIZE));
			}
		}

		for (x = 0; x < PLAYER_INV_WIDTH; x++) {
			this.addSlotToContainer(new Slot(player, x, playerInvStartX + x * SLOT_SIZE, hotbarStartY));
		}
	}

	public static int getPlayerInvStartX(int guiWidth) {
		return (guiWidth - PLAYER_INV_WIDTH * SLOT_SIZE) / 2 + 1;
	}

	@Override
	public void onCraftMatrixChanged(IInventory matrix) {
		this.result.setInventorySlotContents(0, TableRecipeManager.getInstance().findMatchingRecipe(this.matrix, this.tile.getWorld()));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tile.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(slotNumber);

		int size = tile.getSizeInventory();

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			int gridEnd = size + 1;
			int maxSlots = inventorySlots.size();
			if (slotNumber == 0) {
				if (!this.mergeItemStack(itemstack1, gridEnd, maxSlots, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotNumber >= gridEnd && slotNumber < maxSlots) {
				if (!this.mergeItemStack(itemstack1, 1, gridEnd, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, gridEnd, maxSlots, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
			this.onCraftMatrixChanged(this.matrix);
		}

		return itemstack;
	}

}
