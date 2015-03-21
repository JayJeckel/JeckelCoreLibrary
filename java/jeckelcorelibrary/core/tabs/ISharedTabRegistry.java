package jeckelcorelibrary.core.tabs;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface ISharedTabRegistry
{
	public void addMisc(final String modId, final Item item);
	public void addMisc(final String modId, final Item item, final int meta);
	public void addMisc(final String modId, final Block block);
	public void addMisc(final String modId, final Block block, final int meta);

	public void addBlock(final String modId, final Block block);
	public void addBlock(final String modId, final Block block, final int meta);

	public void addItem(final String modId, final Item item);
	public void addItem(final String modId, final Item item, final int meta);
}
