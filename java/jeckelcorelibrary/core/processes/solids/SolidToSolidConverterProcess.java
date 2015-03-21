package jeckelcorelibrary.core.processes.solids;

import jeckelcorelibrary.core.processes.basic.InventoryTickProcess;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SolidToSolidConverterProcess extends InventoryTickProcess
{
	public SolidToSolidConverterProcess(final String processName, final int timeMax, final IInventory inventory, final int slotInputIndex, final int slotOutputIndex, final ItemStack product)
	{
		this(processName, timeMax, false, inventory, slotInputIndex, slotOutputIndex, product);
	}

	public SolidToSolidConverterProcess(final String processName, final int timeMax, final boolean processEarly, final IInventory inventory, final int slotInputIndex, final int slotOutputIndex, final ItemStack product)
	{
		super(processName, timeMax, processEarly, inventory);
		this.setSlotInputIndex(slotInputIndex);
		this.setSlotOutputIndex(slotOutputIndex);
		this.setProductStack(product);
		this.setInputCount(1);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	public ItemStack getProductStack() { return this._product; }
	protected void setProductStack(final ItemStack value) { this._product = value.copy(); }
	private ItemStack _product;

	public int getInputCount() { return this._inputCount; }
	protected void setInputCount(final int value) { this._inputCount = value; }
	protected int _inputCount = 1;

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
