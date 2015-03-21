package jeckelcorelibrary.utils;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Static helper class to centralize nbt related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public class NBTUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private NBTUtil() { }

	public static boolean hasTag(ItemStack stack, String... tagPath)
	{
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) { return false; }
		for (String key : tagPath)
		{
			if (!tag.hasKey(key)) { return false; }
			tag = tag.getCompoundTag(key);
		}
		return true;
	}

	public static NBTTagCompound getTag(ItemStack stack, String... tagPath)
	{
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) { return null; }
		for (String key : tagPath)
		{
			if (!tag.hasKey(key)) { return null; }
			tag = tag.getCompoundTag(key);
		}
		return tag;
	}

	public static NBTTagCompound ensureTag(ItemStack stack, String... tagPath)
	{
		if (stack.getTagCompound() == null) { stack.setTagCompound(new NBTTagCompound()); }
		NBTTagCompound tag = stack.getTagCompound();
		for (String key : tagPath)
		{
			if (!tag.hasKey(key)) { tag.setTag(key, new NBTTagCompound()); }
			tag = tag.getCompoundTag(key);
		}
		return tag;
	}

	public static String[] getTagStringArray(ItemStack stack, int count, String... tagPath)
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
	}

	public static int getTagInteger(ItemStack stack, String... tagPath)
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
	}

	public static ItemStack[] readItemStackArray(NBTTagCompound tagCompound, String tagName, int inventorySize)
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
	}
}
