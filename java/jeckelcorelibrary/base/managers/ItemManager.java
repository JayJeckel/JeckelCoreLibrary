package jeckelcorelibrary.base.managers;

import java.util.ArrayList;
import java.util.List;

import jeckelcorelibrary.api.managers.IItemManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemManager implements IItemManager
{
	private List<ItemStack> _map = new ArrayList<ItemStack>();

	@Override public boolean hasItem(ItemStack input)
	{
		if (input == null) { return false; }
		for (ItemStack stack : this._map) { if (OreDictionary.itemMatches(stack, input, false)) { return true; } }
		return false;
	}

	@Override public void addItem(ItemStack stack)
	{
		if (this.hasItem(stack)) { return; }
		this._map.add(stack.copy());
	}

}
