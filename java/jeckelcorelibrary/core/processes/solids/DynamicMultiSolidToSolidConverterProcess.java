package jeckelcorelibrary.core.processes.solids;

import jeckelcorelibrary.api.managers.IRecipeManager;
import jeckelcorelibrary.core.processes.solids.MultiSolidToSolidConverterProcess;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class DynamicMultiSolidToSolidConverterProcess extends MultiSolidToSolidConverterProcess
{
	public DynamicMultiSolidToSolidConverterProcess(final String processName,
			final IInventory inventory, final int[] slotInputIndexArray, final int[] slotOutputIndexArray,
			final IRecipeManager recipes)
	{
		this(processName, false, inventory, slotInputIndexArray, slotOutputIndexArray, recipes);
	}

	public DynamicMultiSolidToSolidConverterProcess(final String processName, final boolean processEarly,
			final IInventory inventory, final int[] slotInputIndexArray, final int[] slotOutputIndexArray,
			final IRecipeManager recipes)
	{
		super(processName, -1, processEarly, inventory, slotInputIndexArray, slotOutputIndexArray, null);
		this.setPersistTimeMax(true);
		this.setRecipes(recipes);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	protected int getConsumableTimeMax()
	{
		final ItemStack input = this.getSlotStack(this.getSlotInputIndex());
		if (input == null) { return 0; }
		return this.getRecipes().getProcessTime(input);
	}

	public IRecipeManager getRecipes() { return this._recipes; }
	protected void setRecipes(final IRecipeManager value) { this._recipes = value; }
	protected IRecipeManager _recipes = null;

	//@Override public ItemStack getProductStack() { return this.getRecipes().getOutput(this.getSlotStack(this.getSlotInputIndex())); }


	// ##################################################
	//
	// Cycle Methods
	//
	// ##################################################

	@Override protected void doCycleBegin(final World world)
	{
		if (this.getRecipes() != null)
		{
			this.setTimeMax(this.getConsumableTimeMax());
			this.setProductStack(this.getRecipes().getOutput(this.getSlotStack(this.getSlotInputIndex())));
		}
		super.doCycleBegin(world);
	}


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return (this.getConsumableTimeMax() > 0) || (this.isProcessEarly() && this.isProcessing()); }


	// ##################################################
	//
	// Read and Write NBT
	//
	// ##################################################

	protected void readNBTProcess(final NBTTagCompound tagCompound)
	{
		super.readNBTProcess(tagCompound);
		if (tagCompound.hasKey("product"))
		{
			final NBTTagCompound tag = tagCompound.getCompoundTag("product");
			this.setProductStack(ItemStack.loadItemStackFromNBT(tag));
		}
	}

	protected void writeNBTProcess(final NBTTagCompound tagCompound)
	{
		super.writeNBTProcess(tagCompound);
		final ItemStack product = this.getProductStack();
		if (product != null)
		{
			final NBTTagCompound tag = new NBTTagCompound();
			product.writeToNBT(tag);
			tagCompound.setTag("product", tag);
		}
	}
}
