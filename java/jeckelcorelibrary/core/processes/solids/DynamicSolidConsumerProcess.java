package jeckelcorelibrary.core.processes.solids;

import jeckelcorelibrary.api.managers.IConsumableManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class DynamicSolidConsumerProcess extends SolidConsumerProcess
{
	public DynamicSolidConsumerProcess(final String processName,
			final IInventory inventory, final int slotIndex,
			final IConsumableManager consumables)
	{
		this(processName, false, inventory, slotIndex, consumables);
	}

	public DynamicSolidConsumerProcess(final String processName, final boolean processEarly,
			final IInventory inventory, final int slotIndex,
			final IConsumableManager consumables)
	{
		super(processName, -1, processEarly, inventory, slotIndex);
		this.setPersistTimeMax(true);
		this.setSlotIndex(slotIndex);
		this.setConsumables(consumables);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	public IConsumableManager getConsumables() { return this._consumables; }
	protected void setConsumables(final IConsumableManager value) { this._consumables = value; }
	protected IConsumableManager _consumables = null;

	protected int getConsumableTimeMax()
	{
		if (this.getSlotStack() == null) { return 0; }
		return this.getConsumables().getConsumableTime(this.getSlotStack());
	}


	// ##################################################
	//
	// Cycle Methods
	//
	// ##################################################

	@Override protected void doCycleBegin(final World world)
	{
		if (this.getConsumables() != null)
		{
			this.setTimeMax(this.getConsumableTimeMax());
		}
		super.doCycleBegin(world);
	}


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return (this.getConsumableTimeMax() > 0) || (this.isProcessEarly() && this.isProcessing()); }
}
