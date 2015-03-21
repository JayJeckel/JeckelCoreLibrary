package jeckelcorelibrary.core.processes.fluids;

import jeckelcorelibrary.core.processes.basic.TankTickProcess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public class FluidConsumerProcess extends TankTickProcess
{
	public FluidConsumerProcess(final String processName, final int timeMax,
			final FluidTank tank)
	{
		this(processName, timeMax, false, tank);
	}

	public FluidConsumerProcess(final String processName, final int timeMax, final boolean processEarly,
			final FluidTank tank)
	{
		super(processName, timeMax, processEarly, tank);
	}


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessable() { return this.canDrain(1) || (this.isProcessEarly() && this.isProcessing()); }

	@Override public void doProcess(final World world) { this.drain(1); }
}
