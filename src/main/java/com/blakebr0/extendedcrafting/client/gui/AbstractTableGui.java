package com.blakebr0.extendedcrafting.client.gui;

import com.blakebr0.cucumber.util.Utils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import static com.blakebr0.extendedcrafting.client.container.AbstractTableContainer.getPlayerInvStartX;

public class AbstractTableGui extends GuiContainer {
	private final String tier;
	private final int guiStartX;
	private final ResourceLocation gui;

	public AbstractTableGui(Container container, String tier, int guiStartX, ResourceLocation gui) {
		super(container);
		this.tier = tier;
		this.guiStartX = guiStartX;
		this.gui = gui;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.fontRenderer.drawString(Utils.localize("container.ec.table_" + tier), guiStartX, 6, 4210752);
		this.fontRenderer.drawString(Utils.localize("container.inventory"), getPlayerInvStartX(xSize), this.ySize - 94, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(gui);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		drawBackground(x, y);
	}

	protected void drawBackground(int x, int y) {
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}
}
