package jeckelcorelibrary.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockUtil
{
	public static Block getBlock(final ItemStack stack) { return Block.getBlockById(Item.getIdFromItem(stack.getItem())); }

	public static Block getBlock(final Item item) { return Block.getBlockById(Item.getIdFromItem(item)); }

	public static Block getBlock(final ForgeDirection dir, final IBlockAccess world, final int x, final int y, final int z)
	{
		return world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
	}
}
