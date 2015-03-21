package jeckelcorelibrary.core.tabs;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ISharedTabRegistry
{
	public void addMisc(final String modId, final Item item);
	public void addMisc(final String modId, final Item item, final int meta);
	public void addMisc(final String modId, final Block block);
	public void addMisc(final String modId, final Block block, final int meta);
	public void addMisc(final String modId, final ItemStack stack);

	public void addBlock(final String modId, final ItemStack stack);

	public void addItem(final String modId, final ItemStack stack);
}
