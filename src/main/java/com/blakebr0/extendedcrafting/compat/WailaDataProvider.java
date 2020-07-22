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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.List;

import static com.blakebr0.extendedcrafting.compat.WailaDataProvider.TooltipProvider.of;

@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class WailaDataProvider implements IWailaDataProvider {

	public static void callbackRegister(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(of((stack, tooltip, data, config) ->
						tooltip.add(Utils.localize("tooltip.ec.lamp_" + BlockLamp.Type.byMetadata(data.getMetadata()).getName()))),
				BlockLamp.class
		);

		registrar.registerBodyProvider(of((stack, tooltip, data, config) ->
						tooltip.add(Utils.localize("tooltip.ec.trimmed_" + BlockTrimmed.Type.byMetadata(data.getMetadata()).getName()))),
				BlockTrimmed.class
		);

		registrar.registerBodyProvider(checkTile(TilePedestal.class, (stack, tooltip, data, config, pedestal) -> {
					ItemStack result = pedestal.getStack();
					if (!result.isEmpty()) {
						tooltip.add(result.getDisplayName());
					}
				}),
				BlockPedestal.class
		);

		registrar.registerBodyProvider(checkTile(TileCraftingCore.class, (stack, tooltip, data, config, core) -> {
					if (ModConfig.confEnergyInWaila) {
						tooltip.add(Utils.format(core.getEnergy().getEnergyStored()) + " FE");
					}

					CombinationRecipe recipe = core.getRecipe();
					ItemStack output = recipe == null ? ItemStack.EMPTY : recipe.getOutput();
					tooltip.add(Utils.localize("tooltip.ec.crafting", output.getCount(), output.getDisplayName()));
				}),
				BlockCraftingCore.class
		);

		registrar.registerBodyProvider(checkTile(TileAutomationInterface.class, (stack, tooltip, data, config, auto) -> {
					if (ModConfig.confEnergyInWaila) {
						tooltip.add(Utils.format(auto.getEnergy().getEnergyStored()) + " FE");
					}

					ItemStack result = auto.getResult();
					if (!result.isEmpty()) {
						tooltip.add(Utils.localize("tooltip.ec.crafting", result.getCount(), result.getDisplayName()));
					}
				}),
				BlockAutomationInterface.class
		);

		registrar.registerBodyProvider(checkTile(TileEnderCrafter.class, (stack, tooltip, data, config, crafter) -> {
					ItemStack result = crafter.getResult();
					if (!result.isEmpty()) {
						tooltip.add(Utils.localize("tooltip.ec.output", result.getCount(), result.getDisplayName()));
					}
				}),
				BlockEnderCrafter.class
		);

		registrar.registerBodyProvider(checkTile(TileCompressor.class, (stack, tooltip, data, config, compressor) -> {
					if (ModConfig.confEnergyInWaila) {
						tooltip.add(Utils.format(compressor.getEnergy().getEnergyStored()) + " FE");
					}

					CompressorRecipe recipe = compressor.getRecipe();
					ItemStack output = recipe == null ? ItemStack.EMPTY : recipe.getOutput();
					tooltip.add(Utils.localize("tooltip.ec.crafting", output.getCount(), output.getDisplayName()));
				}),
				BlockCompressor.class
		);

		registrar.registerBodyProvider(table(1), BlockBasicTable.class);
		registrar.registerBodyProvider(table(2), BlockAdvancedTable.class);
		registrar.registerBodyProvider(table(3), BlockEliteTable.class);
		registrar.registerBodyProvider(table(4), BlockUltimateTable.class);
	}

	private static TooltipProvider table(int tier) {
		return of((stack, tooltip, data, config) ->
				tooltip.add(Utils.localize("tooltip.ec.tier", tier)));
	}

	private static <T extends TileEntity> TooltipProvider checkTile(Class<T> clazz, TileBodyConsumer<T> consumer) {
		return of((stack, tooltip, data, config) -> {
			TileEntity tile = data.getTileEntity();
			if(clazz.isInstance(tile) && !tile.isInvalid()) {
				consumer.getWailaBody(stack, tooltip, data, config, clazz.cast(tile));
			}
		});
	}

	@FunctionalInterface
	private interface TileBodyConsumer<T> {
		void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor data, IWailaConfigHandler config, T tile);
	}

	@FunctionalInterface
	private interface BodyConsumer {
		void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor data, IWailaConfigHandler config);
	}

	public static class TooltipProvider implements IWailaDataProvider {

		private final BodyConsumer consumer;

		private TooltipProvider(BodyConsumer consumer) {
			this.consumer = consumer;
		}

		public static TooltipProvider of(BodyConsumer consumer) {
			return new TooltipProvider(consumer);
		}

		@Nonnull
		@Override
		public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
			consumer.getWailaBody(itemStack, tooltip, accessor, config);
			return tooltip;
		}
	}
}
