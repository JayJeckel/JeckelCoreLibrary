package jeckelcorelibrary.utils;

import jeckelcorelibrary.api.IInventoryDropFilter;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Static helper class to centralize inventory related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public class InvUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private InvUtil() { }

	/**
	 * Check if the stack can be pushed into the inventory slot. The slot will
	 * be considered valid if it is empty or its contents match the stack
	 * and the total size of the stack plus the slot contents is less than
	 * the stack size limit and the inventory stack size limit.
	 * @param inventory Inventory to check.
	 * @param index Slot index to check.
	 * @param stack Stack to match against.
	 * @return True if the stack will fit in the slot.
	 */
	public static boolean canPushStack(final IInventory inventory, final int index, final ItemStack stack)
	{
		if (inventory == null || index < 0 || stack == null) { return false; }
		final ItemStack slotStack = inventory.getStackInSlot(index);
		if (slotStack == null) { return true; }
		if (!OreDictionary.itemMatches(slotStack, stack, true)) { return false; }
		return (slotStack.stackSize + stack.stackSize <= getMaxStackSize(slotStack, inventory));
	}

	/**
	 * Checks if the inventory slot contains a stack with a size equal to or greater than
	 * the amount.
	 * @param inventory Inventory to check.
	 * @param index Slot index to check.
	 * @param amount Minimum stack size.
	 * @return True if the slot is not empty and meets the minimum amount.
	 */
	public static boolean canPullStack(final IInventory inventory, final int index, final int amount)
	{
		if (inventory == null || index < 0 || amount <= 0) { return false; }
		final ItemStack slotStack = inventory.getStackInSlot(index);
		return (slotStack != null && slotStack.stackSize >= amount);
	}

	/**
	 * Check if the inventory slot contains a matching stack.
	 * @param inventory Inventory to check.
	 * @param index Slot index to check.
	 * @param stack Stack to match against.
	 * @return True if the slot is not empty and contains a matching stack.
	 */
	public static boolean canPullStack(final IInventory inventory, final int index, final ItemStack stack)
	{
		if (inventory == null || index < 0 || stack == null) { return false; }
		final ItemStack slotStack = inventory.getStackInSlot(index);
		if (slotStack == null) { return false; }
		if (!OreDictionary.itemMatches(slotStack, stack, true)) { return false; }
		return (slotStack.stackSize >= stack.stackSize);
	}

	/**
	 * Push a stack into an inventory slot. If the slot is empty, then the stack is added to the slot.
	 * If the slot is not empty, then the stack will be added to the slot if the two stack are equivalent
	 * or nothing will happen if the stacks do not match.
	 * @param inventory Target inventory.
	 * @param index Target slot index.
	 * @param stack Stack being placed in the slot.
	 */
	public static void pushStack(final IInventory inventory, final int index, final ItemStack stack)
	{
		pushStack(inventory, index, stack, false);
	}

	/**
	 * Push a stack into an inventory slot. If the slot is empty, then the stack is added to the slot.
	 * If the slot is not empty, then the stack will be added to the slot if the two stack are equivalent
	 * or nothing will happen if the stacks do not match. However, if overwrite is true, then the stack
	 * will be placed into the slot regardless of the slot's contents, completely replacing any items
	 * already in the slot.
	 * @param inventory Target inventory.
	 * @param index Target slot index.
	 * @param stack Stack being placed in the slot.
	 * @param overwrite True to replace any contents currently in the slot.
	 */
	public static void pushStack(final IInventory inventory, final int index, final ItemStack stack, final boolean overwrite)
	{
		final ItemStack slotStack = inventory.getStackInSlot(index);
		if (slotStack == null || overwrite) { inventory.setInventorySlotContents(index, stack.copy()); }
		else if (slotStack != null && OreDictionary.itemMatches(slotStack, stack, true))
		{
			slotStack.stackSize += stack.stackSize;
			inventory.setInventorySlotContents(index, slotStack);
		}
	}

	/**
	 * Get the maximum stack size taking into account the limits of the stack and the limits of the inventory.
	 * @param stack Required stack to check.
	 * @param inventory Optional inventory to check.
	 * @return Maximum stack size.
	 */
	public static int getMaxStackSize(final ItemStack stack, final IInventory inventory)
	{
		return getMaxStackSize(stack, inventory, null);
	}

	/**
	 * Get the maximum stack size taking into account the limits of the stack and the limits of the
	 * inventory and/or slot, if they are provided. Passing null to inventory or slot will ignore
	 * checking those limits.
	 * @param stack Required stack to check.
	 * @param inventory Optional inventory to check.
	 * @param slot Optional slot to check.
	 * @return Maximum stack size.
	 */
	public static int getMaxStackSize(final ItemStack stack, final IInventory inventory, final Slot slot)
	{
		int max = 64;
		if (stack != null && max > stack.getMaxStackSize()) { max = stack.getMaxStackSize(); }
		if (inventory != null && max > inventory.getInventoryStackLimit()) { max = inventory.getInventoryStackLimit(); }
		if (slot != null && max > slot.getSlotStackLimit()) { max = slot.getSlotStackLimit(); }
		return max;
	}

	public static int getMaxStackSize(final ItemStack stack, final ItemStack slotStack, final IInventory inventory, final Slot slot)
	{
		int max = 64;
		if (stack != null && max > stack.getMaxStackSize()) { max = stack.getMaxStackSize(); }
		if (slotStack != null && max > slotStack.getMaxStackSize()) { max = slotStack.getMaxStackSize(); }
		if (inventory != null && max > inventory.getInventoryStackLimit()) { max = inventory.getInventoryStackLimit(); }
		if (slot != null && max > slot.getSlotStackLimit()) { max = slot.getSlotStackLimit(); }
		return max;
	}

	/**
	 * Find the first slot index that contains an item stack.
	 * @param inventory Inventory to check.
	 * @return Slot index or negative -1.
	 */
	public static int getFirstStackIndex(final IInventory inventory)
	{
		for (int index = 0; index < inventory.getSizeInventory(); index++)
		{
			ItemStack stack = inventory.getStackInSlot(index);
			if (stack != null) { return index; }
		}
		return -1;
	}

	/**
	 * Find the first slot index from the index array that contains an item stack.
	 * @param inventory Inventory to check.
	 * @param indexArray Array of indexes to check.
	 * @return Slot index or negative -1.
	 */
	public static int getFirstStackIndex(final IInventory inventory, final int[] indexArray)
	{
		for (int index : indexArray)
		{
			ItemStack stack = inventory.getStackInSlot(index);
			if (stack != null) { return index; }
		}
		return -1;
	}

	/**
	 * Find the first slot index from the index array that contains
	 * a stack that matches the match argument.
	 * Matching is done using OreDictionary.itemMatches, so the match argument
	 * can match against the metadata wildcard if the strict argument
	 * is false.
	 * @param inventory Inventory to check.
	 * @param indexArray Array of indexes to check.
	 * @param match ItemStack compared to each slot stack.
	 * @param strict Enable strict matching.
	 * @return Slot index or negative -1.
	 */
	public static int getFirstStackIndex(final IInventory inventory, final int[] indexArray, final ItemStack match, final boolean strict)
	{
		for (int index : indexArray)
		{
			ItemStack stack = inventory.getStackInSlot(index);
			if (stack != null && OreDictionary.itemMatches(match, stack, strict)) { return index; }
		}
		return -1;
	}

	/**
	 * Find the first slot index that does not contain an item stack.
	 * @param inventory Inventory to check.
	 * @return Slot index or negative -1.
	 */
	public static int getFirstEmptyIndex(final IInventory inventory)
	{
		for (int index = 0; index < inventory.getSizeInventory(); index++)
		{
			ItemStack stack = inventory.getStackInSlot(index);
			if (stack == null) { return index; }
		}
		return -1;
	}

	/**
	 * Find the first slot index from the index array that does not contain an item stack.
	 * @param inventory Inventory to check.
	 * @param indexArray Array of indexes to check.
	 * @return Slot index or negative -1.
	 */
	public static int getFirstEmptyIndex(final IInventory inventory, final int[] indexArray)
	{
		for (int index : indexArray)
		{
			ItemStack stack = inventory.getStackInSlot(index);
			if (stack == null) { return index; }
		}
		return -1;
	}

	/**
	 * Find the first  slot index from the index array that contains a
	 * matching stack or does not contain an item stack.
	 * Matching is done using OreDictionary.itemMatches, so the match argument
	 * can match against the metadata wildcard if the strict argument
	 * is false.
	 * If total size checking is enabled, then the slot stack and match stack
	 * will have their sizes totaled and validated against both the
	 * stack size max and the inventory stack size max.
	 * @param inventory Inventory to check.
	 * @param indexArray Array of indexes to check.
	 * @param match ItemStack compared to each slot stack.
	 * @param strict Enable strict matching.
	 * @param checkTotalSize Enable checking of totaled stack and match sizes against max stack size.
	 * @return Slot index or negative -1.
	 */
	public static int getFirstMatchOrEmptyIndex(final IInventory inventory, final int[] indexArray, final ItemStack match, final boolean strict, final boolean checkTotalSize)
	{
		int empty = -1;
		for (int index : indexArray)
		{
			final ItemStack stack = inventory.getStackInSlot(index);
			if (stack == null && empty == -1) { empty = index; }
			if (stack != null && OreDictionary.itemMatches(match, stack, strict))
			{
				if (!checkTotalSize) { return index; }
				final int size = stack.stackSize + match.stackSize;
				if (size <= getMaxStackSize(stack, inventory)) { return index; }
			}
		}
		return empty;
	}

	public static void dropInventory(final IInventory inventory, final World world, final double x, final double y, final double z)
	{
		if (inventory != null)
		{
			if (inventory instanceof IInventoryDropFilter)
			{
				InvUtil.dropInventory((IInventoryDropFilter) inventory, world, x, y, z);
				return;
			}
			for (int index = 0; index < inventory.getSizeInventory(); ++index)
			{
				InvUtil.dropInventorySlot(inventory, index, world, x, y, z);
			}
		}
	}

	public static void dropInventory(final IInventoryDropFilter inventory, final World world, final double x, final double y, final double z)
	{
		if (inventory != null)
		{
			for (int index = 0; index < inventory.getSizeInventory(); ++index)
			{
				if (!inventory.canInventoryDropSlot(index)) { continue; }
				InvUtil.dropInventorySlot(inventory, index, world, x, y, z);
			}
		}
	}

	/**
	 * Drop a single slot from an inventory into the world.
	 * @param inventory Inventory to drop from.
	 * @param world Would to drop into.
	 * @param x X location to drop at.
	 * @param y Y location to drop at.
	 * @param z Z location to drop at.
	 * @param slotIndex
	 */
	public static void dropInventorySlot(final IInventory inventory, final int slotIndex, final World world, final double x, final double y, final double z)
	{
		ItemStack slotItemStack = inventory.getStackInSlot(slotIndex);

		if (slotItemStack != null)
		{
			float xRand = MathUtil.rand.nextFloat() * 0.8F + 0.1F;
			float yRand = MathUtil.rand.nextFloat() * 0.8F + 0.1F;
			float zRand = MathUtil.rand.nextFloat() * 0.8F + 0.1F;

			while (slotItemStack.stackSize > 0)
			{
				int dropCount = MathUtil.rand.nextInt(21) + 10;
				if (dropCount > slotItemStack.stackSize) { dropCount = slotItemStack.stackSize; }

				slotItemStack.stackSize -= dropCount;
				EntityItem entityItem = new EntityItem(world, x + xRand, y + yRand, z + zRand, new ItemStack(slotItemStack.getItem(), dropCount, slotItemStack.getItemDamage()));

				if (slotItemStack.hasTagCompound())
				{
					entityItem.getEntityItem().setTagCompound((NBTTagCompound)slotItemStack.getTagCompound().copy());
				}

				float offset = 0.05F;
				entityItem.motionX = (float)MathUtil.rand.nextGaussian() * offset;
				entityItem.motionY = (float)MathUtil.rand.nextGaussian() * offset + 0.2F;
				entityItem.motionZ = (float)MathUtil.rand.nextGaussian() * offset;
				world.spawnEntityInWorld(entityItem);
			}
		}
	}
}
