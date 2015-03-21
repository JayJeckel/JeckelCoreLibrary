package jeckelcorelibrary.core.processes.solids;

import jeckelcorelibrary.core.processes.basic.InventoryTickProcess;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolidToSolidTransferProcess extends InventoryTickProcess
{
	public SolidToSolidTransferProcess(final String processName, final int timeMax,
			final IInventory inventory, final int slotInputIndex, final int slotOutputIndex)
	{
		this(processName, timeMax, false, inventory, slotInputIndex, slotOutputIndex, 1);
	}

	public SolidToSolidTransferProcess(final String processName, final int timeMax,
			final IInventory inventory, final int slotInputIndex, final int slotOutputIndex, final int transferCount)
	{
		this(processName, timeMax, false, inventory, slotInputIndex, slotOutputIndex, transferCount);
	}

	public SolidToSolidTransferProcess(final String processName, final int timeMax, final boolean processEarly,
			final IInventory inventory, final int slotInputIndex, final int slotOutputIndex, final int transferCount)
	{
		super(processName, timeMax, processEarly, inventory);

		this.setSlotInputIndex(slotInputIndex);
		this.setSlotOutputIndex(slotOutputIndex);
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

	public int getSlotInputIndex() { return this._slotInputIndex; }
	protected void setSlotInputIndex(final int value) { this._slotInputIndex = value; }
	protected int _slotInputIndex = -1;

	public ItemStack getSlotInputStack() { return this.getSlotStack(this.getSlotInputIndex()); }

	public int getSlotOutputIndex() { return this._slotOutputIndex; }
	protected void setSlotOutputIndex(final int value) { this._slotOutputIndex = value; }
	protected int _slotOutputIndex = -1;

	public ItemStack getSlotOutputStack() { return this.getInventory().getStackInSlot(this.getSlotOutputIndex()); }


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
		return this.canPushStack(this.getSlotOutputIndex(), new ItemStack(this.getSlotInputStack().getItem(), this.getTransferCount()));
	}
}
