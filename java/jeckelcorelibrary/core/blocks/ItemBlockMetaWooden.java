package jeckelcorelibrary.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMetaWooden extends ItemBlock
{
	public ItemBlockMetaWooden(Block block)
	{
		super(block);
		this.block = block;
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(this.block.getUnlocalizedName().substring(5));
	}

	private final Block block;

	@Override public int getMetadata (int meta) { return meta; }

	@Override public String getUnlocalizedName(ItemStack stack)
	{
		return this.block.getUnlocalizedName() + "_" + BlockMetaWooden.woods[stack.getItemDamage()];
	}
}
