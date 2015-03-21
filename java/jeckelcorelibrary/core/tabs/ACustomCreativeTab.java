package jeckelcorelibrary.core.tabs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ACustomCreativeTab extends SimpleCreativeTab
{
	public ACustomCreativeTab(String title)
	{
		super(title);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override public void displayAllReleventItems(List list)
	{
		final List<ItemStack> stacks = new ArrayList<ItemStack>();
		this.supplyReleventItems(stacks);
		list.addAll(stacks);
	}

	@SideOnly(Side.CLIENT)
	protected abstract void supplyReleventItems(List<ItemStack> stacks);
}
