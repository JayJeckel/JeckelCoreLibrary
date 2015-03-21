package jeckelcorelibrary.api.managers;

import net.minecraft.item.ItemStack;

public interface IRecipeManager
{
	public void addRecipe(ItemStack input, ItemStack output, int processTime);
	public void addRecipe(ItemStack input, String output, int processTime);
	public void addRecipe(String input, ItemStack output, int processTime);
	public void addRecipe(String input, String output, int processTime);

	public int getProcessTime(ItemStack input);

	public ItemStack getOutput(ItemStack input);

	public ItemStack getInput(ItemStack input);
}
