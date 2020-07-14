package com.blakebr0.extendedcrafting.compat.crafttweaker;

import crafttweaker.api.data.IData;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class CraftTweakerUtils {

    @Nonnull
	public static String writeTag(@Nullable NBTBase nbt) {
		return Objects.toString(from(nbt));
	}

	@Nullable
	private static IData from(@Nullable NBTBase nbt) {
        return CraftTweakerMC.getIData(nbt);
    }

    @Nonnull
    public static NonNullList<Ingredient> toIngredients(IIngredient... ingredients) {
        return Arrays.stream(ingredients)
                .map(CraftTweakerUtils::toIngredient)
                .collect(Collectors.toCollection(NonNullList::create));
    }

    @Nonnull
    public static Ingredient toIngredient(@Nullable IIngredient ingredient) {
        return ingredient == null ? Ingredient.EMPTY : new CTIngredientWrapper(ingredient);
    }

    private static class CTIngredientWrapper extends Ingredient {

        private final IIngredient ingredient;

        private CTIngredientWrapper(IIngredient ingredient) {
            super(CraftTweakerMC.getItemStacks(ingredient.getItemArray()));
            this.ingredient = ingredient;
        }

        @Override
        public boolean apply(@Nullable ItemStack stack) {
            if(stack == null) {
                return ingredient.matches((IItemStack) null);
            }
            stack = stack.copy();
            stack.setCount(ingredient.getAmount());
            return ingredient.matches(CraftTweakerMC.getIItemStack(stack));
        }
    }
}
