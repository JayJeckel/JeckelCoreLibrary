package jeckelcorelibrary.core.tabs;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface ISharedTabRegistry
{
	/**
	 * Add the block to the block sub group of the mod id group.
	 * @param modId Id of the mod owning the block.
	 * @param block Block to register.
	 */
	public void addBlock(final String modId, final Block block);

	/**
	 * Add the block to the block sub group of the mod id group.
	 * @param modId Id of the mod owning the block.
	 * @param block Block to register.
	 * @param meta Meta value of the block to register.
	 */
	public void addBlock(final String modId, final Block block, final int meta);

	/**
	 * Add the item to the item sub group of the mod id group.
	 * @param modId Id of the mod owning the item.
	 * @param item Item to register.
	 */
	public void addItem(final String modId, final Item item);

	/**
	 * Add the item to the item sub group of the mod id group.
	 * @param modId Id of the mod owning the item.
	 * @param item Item to register.
	 * @param meta Meta value of the item to register.
	 */
	public void addItem(final String modId, final Item item, final int meta);

	/**
	 * Add the item to the misc sub group of the mod id group.
	 * @param modId Id of the mod owning the item.
	 * @param item Item to register.
	 */
	public void addMisc(final String modId, final Item item);

	/**
	 * Add the item to the misc sub group of the mod id group.
	 * @param modId Id of the mod owning the item.
	 * @param item Item to register.
	 * @param meta Meta value of the item to register.
	 */
	public void addMisc(final String modId, final Item item, final int meta);

	/**
	 * Add the block to the misc sub group of the mod id group.
	 * @param modId Id of the mod owning the block.
	 * @param block Block to register.
	 */
	public void addMisc(final String modId, final Block block);

	/**
	 * Add the block to the misc sub group of the mod id group.
	 * @param modId Id of the mod owning the block.
	 * @param block Block to register.
	 * @param meta Meta value of the block to register.
	 */
	public void addMisc(final String modId, final Block block, final int meta);
}
