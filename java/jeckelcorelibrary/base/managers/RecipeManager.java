package jeckelcorelibrary.base.managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeckelcorelibrary.api.managers.IRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeManager implements IRecipeManager
{
	private Map<ItemStack, ItemStack> _mapOutput = new HashMap<ItemStack, ItemStack>();
	private Map<ItemStack, Integer> _mapTime = new HashMap<ItemStack, Integer>();

	@Override public void addRecipe(ItemStack input, ItemStack output, int processTime)
	{
		if (input == null || output == null) { return; }
		ItemStack key = input.copy();
		this._mapOutput.put(key, output.copy());
		this._mapTime.put(key, processTime);
	}

	@Override public void addRecipe(ItemStack input, String output, int processTime)
	{
		if (input == null || output == null) { return; }
		ItemStack key = input.copy();
		List<ItemStack> values = OreDictionary.getOres(output);
		if (values == null || values.size() == 0) { return; }
		this._mapOutput.put(key, values.get(0).copy());
		this._mapTime.put(key, processTime);
	}

	@Override public void addRecipe(String input, ItemStack output, int processTime)
	{
		if (input == null || output == null) { return; }
		List<ItemStack> keys = OreDictionary.getOres(input);
		if (keys == null || keys.size() == 0) { return; }
		for (ItemStack key : keys)
		{
			this._mapOutput.put(key, output.copy());
			this._mapTime.put(key, processTime);
		}
	}

	@Override public void addRecipe(String input, String output, int processTime)
	{
		if (input == null || output == null) { return; }
		List<ItemStack> keys = OreDictionary.getOres(input);
		if (keys == null || keys.size() == 0) { return; }
		List<ItemStack> values = OreDictionary.getOres(output);
		if (values == null || values.size() == 0) { return; }
		for (ItemStack key : keys)
		{
			this._mapOutput.put(key, values.get(0).copy());
			this._mapTime.put(key, processTime);
		}
	}

	@Override public int getProcessTime(ItemStack input)
	{
		if (input == null) { return 0; }
		for (ItemStack key : this._mapOutput.keySet()) { if (OreDictionary.itemMatches(key, input, false)) { return this._mapTime.get(key); } }
		return 0;
	}

	@Override public ItemStack getOutput(ItemStack input)
	{
		if (input == null) { return null; }
		for (ItemStack key : this._mapOutput.keySet()) { if (OreDictionary.itemMatches(key, input, false)) { return this._mapOutput.get(key).copy(); } }
		return null;
	}

	@Override public ItemStack getInput(ItemStack output)
	{
		if (output == null) { return null; }
		for (ItemStack key : this._mapOutput.keySet()) { if (this._mapOutput.get(key).isItemEqual(output)) { return key.copy(); } }
		return null;
	}

}
