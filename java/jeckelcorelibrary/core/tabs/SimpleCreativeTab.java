package jeckelcorelibrary.core.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SimpleCreativeTab extends CreativeTabs
{
	public SimpleCreativeTab(final String label)
	{
		super(label);
	}

	public SimpleCreativeTab(final int tabIndex, final String label)
	{
		super(tabIndex, label);
	}

	public SimpleCreativeTab(final String label, final ItemStack stack)
	{
		super(label);
		this.setIconItemStack(stack);
	}

	public SimpleCreativeTab(final int tabIndex, final String label, final ItemStack stack)
	{
		super(tabIndex, label);
		this.setIconItemStack(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override public ItemStack getIconItemStack() { return this._stack; }
	public void setIconItemStack(final ItemStack stack) { this._stack = stack.copy(); }
	private ItemStack _stack;

	@SideOnly(Side.CLIENT)
	@Override public Item getTabIconItem() { return (this._stack == null ? null : this.getIconItemStack().getItem()); }

	@SideOnly(Side.CLIENT)
	@Override public int func_151243_f() { return (this._stack == null ? 0 : this.getIconItemStack().getItemDamage()); }
}
