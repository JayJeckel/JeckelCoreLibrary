package jeckelcorelibrary.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Static helper class to centralize nbt related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class NBTUtil
{
	/** This is a "static" class and should not be instanced. */
	private NBTUtil() { }

	/**
	 * Determine if a stack has a specific tag.
	 * @param stack Stack to check.
	 * @param tagPath Tag path to check.
	 * @return True if the tag exists.
	 */
	public static boolean hasTag(final ItemStack stack, final String... tagPath)
	{
		return hasTag(stack.getTagCompound(), tagPath);
	}

	/**
	 * Determine if a compound tag contains a specific tag.
	 * @param tagCompound Compound tag to check.
	 * @param tagPath Tag path to check.
	 * @return True if the tag exists.
	 */
	public static boolean hasTag(final NBTTagCompound tagCompound, final String... tagPath)
	{
		if (tagCompound == null) { return false; }
		NBTTagCompound tag = tagCompound;
		for (String key : tagPath)
		{
			if (!tag.hasKey(key)) { return false; }
			tag = tag.getCompoundTag(key);
		}
		return true;
	}

	/**
	 * Retrieve a nested tag from a stack.
	 * @param stack Stack to check.
	 * @param tagPath Tag path to check.
	 * @return Nest tag or null if not found.
	 */
	public static NBTTagCompound getTag(final ItemStack stack, final String... tagPath)
	{
		return getTag(stack.getTagCompound(), tagPath);
	}

	/**
	 * Retrieve a nested tag.
	 * @param tagCompound Compound tag to check.
	 * @param tagPath Tag path to check.
	 * @return Nested compound tag or null if not found.
	 */
	public static NBTTagCompound getTag(final NBTTagCompound tagCompound, final String... tagPath)
	{
		if (tagCompound == null) { return null; }
		NBTTagCompound tag = tagCompound;
		for (String key : tagPath)
		{
			if (!tag.hasKey(key)) { return null; }
			tag = tag.getCompoundTag(key);
		}
		return tag;
	}

	/**
	 * Retrieve a nested tag or create it if it doesn't exist.
	 * @param stack Stack to check.
	 * @return Stack's compound tag.
	 */
	public static NBTTagCompound ensureTag(final ItemStack stack)
	{
		if (stack.getTagCompound() == null) { stack.setTagCompound(new NBTTagCompound()); }
		return stack.getTagCompound();
	}

	/**
	 * Retrieve a nested tag from a stack or create it if it doesn't exist.
	 * @param stack Stack to check.
	 * @param tagPath Tag path to check.
	 * @return Nested compound tag.
	 */
	public static NBTTagCompound ensureTag(final ItemStack stack, final String... tagPath)
	{
		return ensureTag(ensureTag(stack), tagPath);
	}

	/**
	 * Retrieve a nested tag or create it if it doesn't exist.
	 * @param tagCompound Compound tag to check.
	 * @param tagPath Tag path to check.
	 * @return Nested compound tag.
	 */
	public static NBTTagCompound ensureTag(final NBTTagCompound tagCompound, final String... tagPath)
	{
		if (tagCompound == null) { return null; }// throw here, null isn't valid input
		NBTTagCompound tag = tagCompound;
		for (String key : tagPath)
		{
			if (!tag.hasKey(key)) { tag.setTag(key, new NBTTagCompound()); }
			tag = tag.getCompoundTag(key);
		}
		return tag;
	}

	/*public static String[] getTagStringArray(ItemStack stack, int count, String... tagPath)
	{
		String[] text = new String[count];
		NBTTagCompound tag = getTag(stack, tagPath);
		if (tag == null)
		{
			for (int index = 0; index < count; index++) { text[index] = ""; }
			return text;
		}

		for (int index = 0; index < count; index++)
		{
			text[index] = (tag.hasKey("" + index) ? tag.getString("" + index) : "");
		}
		return text;
	}

	public static void setTagStringArray(ItemStack stack, String[] text, String... tagPath)
	{
		NBTTagCompound tag = ensureTag(stack, tagPath);
		for (int index = 0; index < text.length; index++)
		{
			tag.setString("" + index, text[index]);
		}
	}*/

	/*public static int getTagInteger(ItemStack stack, String... tagPath)
	{
		NBTTagCompound tag = getTag(stack, Arrays.copyOfRange(tagPath, 0, tagPath.length - 1));
		if (tag == null || !tag.hasKey(tagPath[tagPath.length - 1]))
		{
			return 0;
		}
		return tag.getInteger(tagPath[tagPath.length - 1]);
	}

	public static void setTagInteger(ItemStack stack, int value, String... tagPath)
	{
		NBTTagCompound tag = ensureTag(stack, Arrays.copyOfRange(tagPath, 0, tagPath.length - 1));
		tag.setInteger(tagPath[tagPath.length - 1], value);
	}*/

	/*public static ItemStack[] readItemStackArray(NBTTagCompound tagCompound, String tagName, int inventorySize)
	{
		ItemStack[] stacks = new ItemStack[inventorySize];

		NBTTagList tagList = tagCompound.getTagList(tagName + "." + "items", 10);
		for (int tagIndex = 0; tagIndex < tagList.tagCount(); ++tagIndex)
		{
			NBTTagCompound tag = (NBTTagCompound)tagList.getCompoundTagAt(tagIndex);
			byte slotIndex = tag.getByte("slot");

			if (slotIndex >= 0 && slotIndex < stacks.length)
			{
				stacks[slotIndex] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		return stacks;
	}

	public static void writeItemStackArray(NBTTagCompound tagCompound, String tagName, ItemStack[] stacks)
	{
		NBTTagList tagList;

		// Items
		tagList = new NBTTagList();
		for (int slotIndex = 0; slotIndex < stacks.length; ++slotIndex)
		{
			if (stacks[slotIndex] != null)
			{
				NBTTagCompound slotTag = new NBTTagCompound();
				slotTag.setByte("slot", (byte)slotIndex);
				stacks[slotIndex].writeToNBT(slotTag);
				tagList.appendTag(slotTag);
			}
		}
		tagCompound.setTag(tagName + "." + "items", tagList);
	}*/
}
