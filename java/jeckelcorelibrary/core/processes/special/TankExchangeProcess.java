package jeckelcorelibrary.core.processes.special;

import jeckelcorelibrary.core.processes.basic.DualTickProcess;
import jeckelcorelibrary.utils.FluidUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TankExchangeProcess extends DualTickProcess
{
	public TankExchangeProcess(String processName, int timeMax,
			final IInventory inventory, FluidTank tank, final int slotInputIndex, final int slotOutputIndex)
	{
		this(processName, timeMax, false, inventory, tank, slotInputIndex, slotOutputIndex);
	}

	public TankExchangeProcess(String processName, int timeMax, boolean processEarly,
			final IInventory inventory, FluidTank tank, final int slotInputIndex, final int slotOutputIndex)
	{
		super(processName, timeMax, processEarly, inventory, tank);
		this.setSlotInputIndex(slotInputIndex);
		this.setSlotOutputIndex(slotOutputIndex);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

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

	@Override public boolean isProcessable() { return (this.canWork()) || (this.isProcessEarly() && this.isProcessing()); }

	@Override protected void doProcess(final World world)
	{
		final ItemStack inputStack = this.getSlotInputStack();

		ItemStack outputStack = null;
		if (FluidUtil.isEmptyContainer(inputStack))
		{
			outputStack = FluidUtil.getFilledContainer(this.getTank().getFluid(), inputStack);
			final FluidStack outputFluid = FluidUtil.getFluid(outputStack);
			this.getTank().drain(outputFluid.amount, true);
		}
		else if (FluidUtil.isFilledContainer(inputStack))
		{
			final FluidStack inputFluid = FluidUtil.getFluid(inputStack);
			outputStack = FluidUtil.getEmptyContainer(inputStack);
			this.fill(inputFluid);
		}

		this.decSlotStack(this.getSlotInputIndex(), 1);
		this.setSlotStack(this.getSlotOutputIndex(), outputStack, false);
	}


	// ##################################################
	//
	// Helper Methods
	//
	// ##################################################

	public boolean canWork()
	{
		final ItemStack inputStack = this.getSlotInputStack();
		if (inputStack == null) { return false; }

		ItemStack outputStack = null;
		if (FluidUtil.isEmptyContainer(inputStack))
		{
			outputStack = FluidUtil.getFilledContainer(this.getTank().getFluid(), inputStack);
			if (outputStack == null) { return false; }
			final FluidStack outputFluid = FluidUtil.getFluid(outputStack);
			if (outputFluid == null) { return false; }
			if (!this.canDrain(outputFluid.amount)) { return false; }
		}
		else if (FluidUtil.isFilledContainer(inputStack))
		{
			final FluidStack inputFluid = FluidUtil.getFluid(inputStack);
			if (!this.canFill(inputFluid)) { return false; }
			outputStack = FluidUtil.getEmptyContainer(inputStack);
			if (outputStack == null) { return false; }
		}
		else
		{
			return false;
		}

		if (outputStack != null && this.getSlotOutputStack() != null)
		{
			if (!this.getSlotOutputStack().isItemEqual(outputStack)) { return false; }
			int newSize = this.getSlotOutputStack().stackSize + 1;
			if (newSize > outputStack.getItem().getItemStackLimit(outputStack)) { return false; }
		}
		return true;
	}
}
