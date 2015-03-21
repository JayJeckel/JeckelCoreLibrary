package jeckelcorelibrary.base.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeckelcorelibrary.api.managers.IConsumableManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ConsumableManager implements IConsumableManager
{
	private Map<ItemStack, Integer> _map = new HashMap<ItemStack, Integer>();

	@Override public int getConsumableTime(ItemStack fuel)
	{
		if (fuel == null) { return 0; }
		for (ItemStack stack : this._map.keySet()) { if (OreDictionary.itemMatches(stack, fuel, false)) { return this._map.get(stack); } }
		return 0;
	}

	@Override public void setConsumable(ItemStack fuel, int processTime)
	{
		this._map.put(fuel.copy(), processTime);
	}

	@Override public void setConsumable(String fuel, int processTime)
	{
		List<ItemStack> stacks = OreDictionary.getOres(fuel);
		for (ItemStack stack : stacks)
		{
			this._map.put(stack.copy(), processTime);
		}
	}

	@Override
	public void delConsumable(ItemStack fuel)
	{
	}

	@Override
	public void delConsumable(String fuel)
	{
	}
}
