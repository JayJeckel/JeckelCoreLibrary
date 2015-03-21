package jeckelcorelibrary.core.processes.basic;

import jeckelcorelibrary.core.guis.SynchList;
import jeckelcorelibrary.core.guis.TankSynchHandlers.AmountAdditional;
import jeckelcorelibrary.core.guis.TankSynchHandlers.AmountPrimary;
import jeckelcorelibrary.utils.InvUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class DualTickProcess extends TickProcess
{
	public DualTickProcess(final String processName, final int timeMax,
			final IInventory inventory, final FluidTank tank)
	{
		this(processName, timeMax, false, inventory, tank);
	}

	public DualTickProcess(final String processName, final int timeMax, final boolean processEarly,
			final IInventory inventory, final FluidTank tank)
	{
		super(processName, timeMax);
		this.setProcessEarly(processEarly);
		this.setInventory(inventory);
		this.setTank(tank);
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

	public FluidTank getTank() { return this._tank; }
	protected void setTank(FluidTank value) { this._tank = value; }
	protected FluidTank _tank = null;


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
	// Inventory Helper Methods
	//
	// ##################################################

	protected int getMaxStackSize(final ItemStack stack) { return InvUtil.getMaxStackSize(stack, this.getInventory(), null); }

	protected ItemStack getSlotStack(final int index) { return this.getInventory().getStackInSlot(index); }

	protected ItemStack decSlotStack(final int index) { return this.getInventory().decrStackSize(index, 1); }
	protected ItemStack decSlotStack(final int index, final int amount) { return this.getInventory().decrStackSize(index, amount); }

	protected boolean canPushStack(final int index, final ItemStack stack) { return InvUtil.canPushStack(this.getInventory(), index, stack); }

	protected boolean canPullStack(final int index, final int amount) { return InvUtil.canPullStack(this.getInventory(), index, amount); }

	protected void setSlotStack(final int index, final ItemStack stack, final boolean overwrite) { InvUtil.pushStack(this.getInventory(), index, stack, overwrite); }


	// ##################################################
	//
	// Tank Helper Methods
	//
	// ##################################################

	protected boolean canDrain(final int amount)
	{
		final FluidStack stack = this.getTank().drain(amount, false);
		return stack != null && stack.amount == amount;
	}

	protected FluidStack drain(final int amount)
	{
		return this.getTank().drain(amount, true);
	}

	protected boolean canFill(final FluidStack fluid)
	{
		if (fluid == null) { return false; }
		return this.getTank().fill(fluid, false) == fluid.amount;
	}

	protected int fill(final FluidStack fluid)
	{
		return this.getTank().fill(fluid, true);
	}


	// ##################################################
	//
	// Synch Handler Methods
	//
	// ##################################################

	public void supplySynchHandlers(final SynchList list)
	{
		super.supplySynchHandlers(list);
		list.addTankFluidId(this.getTank());
		if (this.getTank().getCapacity() <= 32000)
		{
			list.addTankFluidAmount(this.getTank());
		}
		else if (this.getTank().getCapacity() <= 64000)
		{
			list.add(new AmountPrimary(this.getTank()));
			list.add(new AmountAdditional(this.getTank()));
		}
		else
		{
			throw new IllegalArgumentException("Fluid capacities larger than 64000 can not be handled.");
		}
	}
}
