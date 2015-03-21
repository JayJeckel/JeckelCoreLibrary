package jeckelcorelibrary.core.processes.solids;

import jeckelcorelibrary.core.processes.basic.InventoryTickProcess;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolidProducerProcess extends InventoryTickProcess
{
	public SolidProducerProcess(final String processName, final int timeMax, final IInventory inventory, final int slotIndex, final ItemStack product)
	{
		this(processName, timeMax, false, inventory, slotIndex, product);
	}

	public SolidProducerProcess(final String processName, final int timeMax, final boolean processEarly, final IInventory inventory, final int slotIndex, final ItemStack product)
	{
		super(processName, timeMax, processEarly, inventory);
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
	protected void setProductStack(final ItemStack value) { this._product = value.copy(); }
	private ItemStack _product;


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return (this.canPushStack(this.getSlotIndex(), this.getProductStack())) || (this.isProcessEarly() && this.isProcessing()); }

	@Override protected void doProcess(final World world)
	{
		this.setSlotStack(this.getSlotIndex(), this.getProductStack(), false);
	}
}
