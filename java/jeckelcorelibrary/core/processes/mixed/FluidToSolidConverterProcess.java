package jeckelcorelibrary.core.processes.mixed;

import jeckelcorelibrary.core.processes.basic.DualTickProcess;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public class FluidToSolidConverterProcess extends DualTickProcess
{
	public FluidToSolidConverterProcess(final String processName, final int timeMax,
			final IInventory inventory, final FluidTank tank, final int slotIndex, final ItemStack product)
	{
		this(processName, timeMax, false, inventory, tank, slotIndex, product);
	}

	public FluidToSolidConverterProcess(final String processName, final int timeMax, final boolean processEarly,
			final IInventory inventory, final FluidTank tank, final int slotIndex, final ItemStack product)
	{
		super(processName, timeMax, processEarly, inventory, tank);
		this.setSlotIndex(slotIndex);
		this.setProductStack(product);
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

	public ItemStack getProductStack() { return this._product; }
	protected void setProductStack(ItemStack value) { this._product = value.copy(); }
	private ItemStack _product;


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return (this.canInput() && this.canOutput()) || (this.isProcessEarly() && this.isProcessing()); }

	@Override protected void doProcess(final World world)
	{
		this.drain(1);
		this.setSlotStack(this.getSlotIndex(), this.getProductStack(), true);
	}


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	protected boolean canInput()
	{
		return this.canDrain(1);
	}

	protected boolean canOutput()
	{
		return this.canPushStack(this.getSlotIndex(), this.getProductStack());
	}
}
