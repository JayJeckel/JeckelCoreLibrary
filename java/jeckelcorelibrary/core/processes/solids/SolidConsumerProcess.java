package jeckelcorelibrary.core.processes.solids;

import jeckelcorelibrary.core.processes.basic.InventoryTickProcess;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolidConsumerProcess extends InventoryTickProcess
{
	public SolidConsumerProcess(final String processName, final int timeMax, final IInventory inventory, final int slotIndex)
	{
		this(processName, timeMax, false, inventory, slotIndex);
	}

	public SolidConsumerProcess(final String processName, final int timeMax, final boolean processEarly, final IInventory inventory, final int slotIndex)
	{
		super(processName, timeMax, processEarly, inventory);
		this.setSlotIndex(slotIndex);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	public int getSlotIndex() { return this._slotIndex; }
	protected void setSlotIndex(final int value) { this._slotIndex = value; }
	protected int _slotIndex = -1;

	public ItemStack getSlotStack() { return this.getSlotStack(this.getSlotIndex()); }


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return this.getSlotStack() != null || (this.isProcessEarly() && this.isProcessing()); }

	@Override protected void doProcess(final World world) { this.decSlotStack(this.getSlotIndex(), 1); }
}
