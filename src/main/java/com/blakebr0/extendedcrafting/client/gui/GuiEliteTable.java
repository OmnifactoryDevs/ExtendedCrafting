package com.blakebr0.extendedcrafting.client.gui;

import com.blakebr0.cucumber.helper.ResourceHelper;
import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.client.container.ContainerEliteTable;
import net.minecraft.util.ResourceLocation;

public class GuiEliteTable extends AbstractTableGui {

	public static final ResourceLocation GUI = ResourceHelper.getResource(ExtendedCrafting.MOD_ID, "textures/gui/elite_table.png");

	public GuiEliteTable(ContainerEliteTable container) {
		super(container, "elite", ContainerEliteTable.GRID_START_X, GUI);
		this.xSize = ContainerEliteTable.X_SIZE;
		this.ySize = ContainerEliteTable.Y_SIZE;
	}

}
