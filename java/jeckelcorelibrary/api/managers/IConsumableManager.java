package jeckelcorelibrary.api.managers;

import net.minecraft.item.ItemStack;

public interface IConsumableManager
{
	public int getConsumableTime(ItemStack fuel);

	public void setConsumable(ItemStack fuel, int processTime);
	public void setConsumable(String fuel, int processTime);
	
	public void delConsumable(ItemStack fuel);
	public void delConsumable(String fuel);
}
