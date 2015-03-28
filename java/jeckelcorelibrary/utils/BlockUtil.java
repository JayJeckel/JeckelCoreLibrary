package jeckelcorelibrary.utils;

import jeckelcorelibrary.core.BlockPosition;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Static helper class to centralize block related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public final class BlockUtil
{
	/**
	 * This is a "static" class and should not be instanced.
	 */
	private BlockUtil() { }

	/**
	 * Return the block related to the item stack.
	 * @param stack Item stack to get block of.
	 * @return Block or null.
	 */
	public static Block getBlock(final ItemStack stack) { return BlockUtil.getBlock(stack.getItem()); }

	/**
	 * Return the block related to the item.
	 * @param item Item to get block of.
	 * @return Block or null.
	 */
	public static Block getBlock(final Item item) { return (Block)Block.blockRegistry.getObjectById(Item.getIdFromItem(item)); }

	/**
	 * Get the block in the direction from the coordinates.
	 * @param dir Direction to get block.
	 * @param world World instance.
	 * @param x X coordinate to start from.
	 * @param y Y coordinate to start from.
	 * @param z Z coordinate to start from.
	 * @return Block instance.
	 */
	public static Block getBlock(final ForgeDirection dir, final IBlockAccess world, final int x, final int y, final int z) { return BlockUtil.getBlock(dir, 1, world, x, y, z); }

	/**
	 * Get the block that is distance from the coordinates in the direction.
	 * @param dir Direction to get block.
	 * @param distance Distance to get block.
	 * @param world World instance.
	 * @param x X coordinate to start from.
	 * @param y Y coordinate to start from.
	 * @param z Z coordinate to start from.
	 * @return Block instance.
	 */
	public static Block getBlock(final ForgeDirection dir, final int distance, final IBlockAccess world, final int x, final int y, final int z)
	{
		final int xTarget = x + (dir.offsetX * distance);
		final int yTarget = y + (dir.offsetY * distance);
		final int zTarget = z + (dir.offsetZ * distance);
		return world.getBlock(xTarget, yTarget, zTarget);
	}

	/**
	 * Get position offset in the given direction.
	 * @param dir Direction to offset position.
	 * @param position Position to offset.
	 * @return Offset position.
	 */
	public static BlockPosition getBlockPosition(final ForgeDirection dir, final BlockPosition position) { return BlockUtil.getBlockPosition(dir, 1, position.x, position.y, position.z); }

	/**
	 * Get position offset in the given direction.
	 * @param dir Direction to offset position.
	 * @param distance Distance to offset position.
	 * @param position Position to offset.
	 * @return Offset position.
	 */
	public static BlockPosition getBlockPosition(final ForgeDirection dir, final int distance, final BlockPosition position) { return BlockUtil.getBlockPosition(dir, distance, position.x, position.y, position.z); }

	/**
	 * Get position offset in the given direction.
	 * @param dir Direction to offset position.
	 * @param x X coordinate to offset.
	 * @param y Y coordinate to offset.
	 * @param z Z coordinate to offset.
	 * @return Offset position.
	 */
	public static BlockPosition getBlockPosition(final ForgeDirection dir, final int x, final int y, final int z) { return BlockUtil.getBlockPosition(dir, 1, x, y, z); }

	/**
	 * Get position offset in the given direction.
	 * @param dir Direction to offset position.
	 * @param distance Distance to offset position.
	 * @param x X coordinate to offset.
	 * @param y Y coordinate to offset.
	 * @param z Z coordinate to offset.
	 * @return Offset position.
	 */
	public static BlockPosition getBlockPosition(final ForgeDirection dir, final int distance, final int x, final int y, final int z)
	{
		final int xTarget = x + (dir.offsetX * distance);
		final int yTarget = y + (dir.offsetY * distance);
		final int zTarget = z + (dir.offsetZ * distance);
		return new BlockPosition(xTarget, yTarget, zTarget);
	}
}
