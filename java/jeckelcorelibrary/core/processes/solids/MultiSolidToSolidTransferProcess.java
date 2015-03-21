package jeckelcorelibrary.core.processes.solids;

import jeckelcorelibrary.core.processes.basic.InventoryTickProcess;
import jeckelcorelibrary.utils.InvUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MultiSolidToSolidTransferProcess extends InventoryTickProcess
{
	public MultiSolidToSolidTransferProcess(final String processName, final int timeMax,
			final IInventory inventory, final int[] slotInputIndexArray, final int[] slotOutputIndexArray)
	{
		this(processName, timeMax, false, inventory, slotInputIndexArray, slotOutputIndexArray, 1);
	}

	public MultiSolidToSolidTransferProcess(final String processName, final int timeMax,
			final IInventory inventory, final int[] slotInputIndexArray, final int[] slotOutputIndexArray, final int transferCount)
	{
		this(processName, timeMax, false, inventory, slotInputIndexArray, slotOutputIndexArray, transferCount);
	}

	public MultiSolidToSolidTransferProcess(final String processName, final int timeMax, final boolean processEarly,
			final IInventory inventory, final int[] slotInputIndexArray, final int[] slotOutputIndexArray, final int transferCount)
	{
		super(processName, timeMax, processEarly, inventory);
		this.setSlotInputIndexArray(slotInputIndexArray);
		this.setSlotOutputIndexArray(slotOutputIndexArray);
		this.setTransferCount(transferCount);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	public int getTransferCount() { return this._transferCount; }
	protected void setTransferCount(final int value) { this._transferCount = value; }
	protected int _transferCount = 1;

	public int[] getSlotInputIndexArray() { return this._slotInputIndexArray; }
	protected void setSlotInputIndexArray(final int[] value) { this._slotInputIndexArray = value; }
	protected int[] _slotInputIndexArray = null;

	public int getSlotInputIndex() { return InvUtil.getFirstStackIndex(this.getInventory(), this.getSlotInputIndexArray()); }

	public int[] getSlotOutputIndexArray() { return this._slotOutputIndexArray; }
	protected void setSlotOutputIndexArray(final int[] value) { this._slotOutputIndexArray = value; }
	protected int[] _slotOutputIndexArray = null;

	public int getSlotOutputIndex() { return InvUtil.getFirstMatchOrEmptyIndex(this.getInventory(), this.getSlotOutputIndexArray(), this.getSlotStack(this.getSlotInputIndex()), true, false); }


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return (this.canInput() && this.canOutput()) || (this.isProcessEarly() && this.isProcessing()); }

	@Override protected void doProcess(final World world)
	{
		final ItemStack cargo = this.decSlotStack(this.getSlotInputIndex(), this.getTransferCount());
		this.setSlotStack(this.getSlotOutputIndex(), cargo, false);
	}


	// ##################################################
	//
	// Helper Methods
	//
	// ##################################################

	protected boolean canInput() { return this.canPullStack(this.getSlotInputIndex(), this.getTransferCount()); }

	protected boolean canOutput()
	{
		return this.canPushStack(this.getSlotOutputIndex(), new ItemStack(this.getSlotStack(this.getSlotInputIndex()).getItem(), this.getTransferCount()));
	}
}
