package jeckelcorelibrary.api.managers;

import net.minecraft.item.ItemStack;

public interface IItemManager
{
	public boolean hasItem(ItemStack stack);
	
	public void addItem(ItemStack stack);
}
