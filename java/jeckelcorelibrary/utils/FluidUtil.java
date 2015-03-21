package jeckelcorelibrary.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;

/**
 * Static helper class to centralize fluid related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class FluidUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private FluidUtil() { }

	/**
	 * Get the Id number of the stack.
	 * @param fluid Fluid stack to get the id from.
	 * @return Id number of the fluid or -1 if fluid is null.
	 */
	public static int getId(FluidStack fluid) { return (fluid == null ? -1 : fluid.fluidID); }

	/**
	 * Get the amount of fluid in the stack.
	 * @param fluid Fluid stack to get the amount of.
	 * @return The amount of fluid in the stack or 0 if null.
	 */
	public static int getAmount(FluidStack fluid) { return (fluid == null ? 0 : fluid.amount); }

	/**
	 * Get the localized display name of the stack.
	 * @param fluid Fluid stack to get the name of.
	 * @return The localized name of the stack or an empty string if null.
	 */
	public static String getDisplayName(FluidStack fluid) { return (fluid == null ? "" : fluid.getFluid().getBlock().getLocalizedName()); }

	/**
	 * Get the fluid in the item stack.
	 * @param stack Item stack to get the fluid from.
	 * @return The fluid stack from the item stack or null if the item stack is null.
	 */
	public static FluidStack getFluid(ItemStack stack) { return FluidContainerRegistry.getFluidForFilledItem(stack); }

	/**
	 * Check if the stack is an empty fluid container.
	 * @param stack Stack to check.
	 * @return True if the stack is a valid empty fluid container.
	 */
	public static boolean isEmptyContainer(ItemStack stack) { return FluidContainerRegistry.isEmptyContainer(stack); }

	/**
	 * Check if the stack is an empty fluid container that can hold the given fluid.
	 * @param stack Stack to check.
	 * @param fluid Fluid to check against.
	 * @return True if the stack is a valid empty fluid container.
	 */
	public static boolean isEmptyContainer(ItemStack stack, FluidStack fluid)
	{
		if (!FluidUtil.isEmptyContainer(stack)) { return false; }
		return FluidUtil.getFilledContainer(fluid, stack) != null;
	}

	/**
	 * Check if the stack is a filled fluid container.
	 * @param stack Stack to check.
	 * @return True if the stack is a valid filled fluid container.
	 */
	public static boolean isFilledContainer(ItemStack stack) { return FluidContainerRegistry.isFilledContainer(stack); }

	/**
	 * Check if the stack is a filled fluid container containing the given fluid.
	 * @param stack Stack to check.
	 * @param fluid Fluid to check against.
	 * @return True if the stack is a valid filled fluid container.
	 */
	public static boolean isFilledContainer(ItemStack stack, FluidStack fluid)
	{
		if (!FluidContainerRegistry.isFilledContainer(stack)) { return false; }
		return FluidContainerRegistry.containsFluid(stack, fluid);
	}

	/**
	 * Get the empty fluid container for the passed filled fluid container.
	 * @param filled Filled fluid container.
	 * @return The related empty fluid container or null.
	 */
	public static ItemStack getEmptyContainer(ItemStack filled)
	{
		if (filled == null) { return null; }

		FluidStack fluid = FluidUtil.getFluid(filled);
		if (fluid == null) { return null; }

		for (FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData())
		{
			if (data.fluid.isFluidEqual(fluid) && data.filledContainer.isItemEqual(filled))
			{
				return data.emptyContainer.copy();
			}
		}
		return null;
	}

	/**
	 * Get the filled fluid container for the passed fluid and empty fluid container.
	 * @param fluid Fluid to find filled fluid container for.
	 * @param empty Empty fluid container.
	 * @return The related filled fluid container or null.
	 */
	public static ItemStack getFilledContainer(FluidStack fluid, ItemStack empty)
	{
		return FluidContainerRegistry.fillFluidContainer(fluid, empty);
	}
}