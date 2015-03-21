package jeckelcorelibrary.api;

import net.minecraft.inventory.IInventory;

public interface IInventoryDropFilter extends IInventory
{
	public boolean canInventoryDropSlot(int index);
}
