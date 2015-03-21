package jeckelcorelibrary.core.processes.fluids;

import jeckelcorelibrary.core.processes.basic.TankTickProcess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidProducerProcess extends TankTickProcess
{
	public FluidProducerProcess(final String processName, final int timeMax,
			final FluidTank tank, final FluidStack product)
	{
		this(processName, timeMax, false, tank, product);
	}

	public FluidProducerProcess(final String processName, final int timeMax, final boolean processEarly,
			final FluidTank tank, final FluidStack product)
	{
		super(processName, timeMax, processEarly, tank);
		this.setProductStack(product);
	}


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	public FluidStack getProductStack() { return this._product; }
	protected void setProductStack(final FluidStack value) { this._product = value.copy(); }
	private FluidStack _product;


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return this.canFill(this.getProductStack()) || (this.isProcessEarly() && this.isProcessing()); }

	@Override public void doProcess(final World world) { this.fill(this.getProductStack()); }
}
