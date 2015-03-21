package jeckelcorelibrary.core.processes.solids;

import jeckelcorelibrary.core.processes.basic.InventoryTickProcess;
import jeckelcorelibrary.utils.InvUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MultiSolidToSolidConverterProcess extends InventoryTickProcess
{
	public MultiSolidToSolidConverterProcess(final String processName, final int timeMax,
			final IInventory inventory, final int[] slotInputIndexArray, final int[] slotOutputIndexArray, final ItemStack product)
	{
		this(processName, timeMax, false, inventory, slotInputIndexArray, slotOutputIndexArray, product);
	}

	public MultiSolidToSolidConverterProcess(final String processName, final int timeMax, final boolean processEarly,
			final IInventory inventory, final int[] slotInputIndexArray, final int[] slotOutputIndexArray, final ItemStack product)
	{
		super(processName, timeMax, processEarly, inventory);
		this.setSlotInputIndexArray(slotInputIndexArray);
		this.setSlotOutputIndexArray(slotOutputIndexArray);
		this.setProductStack(product);
		this.setInputCount(1);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	public ItemStack getProductStack() { return this._product; }
	protected void setProductStack(final ItemStack value) { this._product = (value == null ? null : value.copy()); }
	private ItemStack _product;

	public int getInputCount() { return this._inputCount; }
	protected void setInputCount(final int value) { this._inputCount = value; }
	protected int _inputCount = 1;

	public int[] getSlotInputIndexArray() { return this._slotInputIndexArray; }
	protected void setSlotInputIndexArray(final int[] value) { this._slotInputIndexArray = value; }
	protected int[] _slotInputIndexArray = null;

	public int getSlotInputIndex() { return InvUtil.getFirstStackIndex(this.getInventory(), this.getSlotInputIndexArray()); }

	public int[] getSlotOutputIndexArray() { return this._slotOutputIndexArray; }
	protected void setSlotOutputIndexArray(final int[] value) { this._slotOutputIndexArray = value; }
	protected int[] _slotOutputIndexArray = null;

	public int getSlotOutputIndex() { return InvUtil.getFirstMatchOrEmptyIndex(this.getInventory(), this.getSlotOutputIndexArray(), this.getProductStack(), true, false); }


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return (this.canInput() && this.canOutput()) || (this.isProcessEarly() && this.isProcessing()); }

	@Override protected void doProcess(final World world)
	{
		this.decSlotStack(this.getSlotInputIndex(), this.getInputCount());
		this.setSlotStack(this.getSlotOutputIndex(), this.getProductStack(), false);
	}


	// ##################################################
	//
	// Helper Methods
	//
	// ##################################################

	protected boolean canInput() { return this.canPullStack(this.getSlotInputIndex(), this.getInputCount()); }

	protected boolean canOutput()
	{
		return this.canPushStack(this.getSlotOutputIndex(), this.getProductStack());
	}
}
