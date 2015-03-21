package jeckelcorelibrary.core.processes.basic;

import jeckelcorelibrary.utils.InvUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InventoryTickProcess extends TickProcess
{
	public InventoryTickProcess(final String processName, final int timeMax, final IInventory inventory)
	{
		this(processName, timeMax, false, inventory);
	}

	public InventoryTickProcess(final String processName, final int timeMax, final boolean processEarly, final IInventory inventory)
	{
		super(processName, timeMax);
		this.setProcessEarly(processEarly);
		this.setInventory(inventory);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	public boolean isProcessEarly() { return this._processEarly; }
	protected void setProcessEarly(final boolean value) { this._processEarly = value; }
	protected boolean _processEarly = false;

	public IInventory getInventory() { return this._inventory; }
	protected void setInventory(final IInventory value) { this._inventory = value; }
	protected IInventory _inventory = null;


	// ##################################################
	//
	// Cycle Methods
	//
	// ##################################################

	@Override protected void doCycleBegin(final World world) { if (this.isProcessEarly()) { this.doProcess(world); } }

	@Override protected void doCycleEnd(final World world) { if (!this.isProcessEarly()) { this.doProcess(world); } }


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	protected void doProcess(final World world) { }


	// ##################################################
	//
	// Helper Methods
	//
	// ##################################################

	protected int getMaxStackSize(final ItemStack stack) { return InvUtil.getMaxStackSize(stack, this.getInventory(), null); }

	protected ItemStack getSlotStack(final int index) { return (index < 0 ? null : this.getInventory().getStackInSlot(index)); }

	protected ItemStack decSlotStack(final int index) { return this.getInventory().decrStackSize(index, 1); }
	protected ItemStack decSlotStack(final int index, final int amount) { return this.getInventory().decrStackSize(index, amount); }

	protected boolean canPushStack(final int index, final ItemStack stack) { return InvUtil.canPushStack(this.getInventory(), index, stack); }

	protected boolean canPullStack(final int index, final int amount) { return InvUtil.canPullStack(this.getInventory(), index, amount); }
	protected boolean canPullStack(final int index, final ItemStack stack) { return InvUtil.canPullStack(this.getInventory(), index, stack); }

	protected void setSlotStack(final int index, final ItemStack stack, final boolean overwrite) { InvUtil.pushStack(this.getInventory(), index, stack, overwrite); }
}
