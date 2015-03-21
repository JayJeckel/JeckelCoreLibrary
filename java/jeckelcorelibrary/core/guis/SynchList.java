package jeckelcorelibrary.core.guis;

import java.util.ArrayList;

import jeckelcorelibrary.api.guis.ISynchHandler;
import jeckelcorelibrary.api.processes.ITickProcess;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.fluids.FluidTank;

public class SynchList extends ArrayList<ISynchHandler>
{
	private static final long serialVersionUID = 5587013053157293975L;


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	public void sendAll(final ICrafting crafting, final Container container)
	{
		this.send(crafting, container, false);
	}

	public void sendChanges(final ICrafting crafting, final Container container)
	{
		this.send(crafting, container, true);
	}

	public void updateProgress(final int index, final int value)
	{
		if (index < 0 || index >= this.size()) { return; }
		this.get(index).commit(value);
	}

	public void send(final ICrafting crafting, final Container container, final boolean compare)
	{
		final int size = this.size();
		for (int index = 0; index < size; index++)
		{
			final ISynchHandler handler = this.get(index);
			handler.send(index, crafting, container, compare);
		}
	}

	public void synchChanges()
	{
		final int size = this.size();
		for (int index = 0; index < size; index++)
		{
			final ISynchHandler handler = this.get(index);
			handler.synch();
		}
	}


	// ##################################################
	//
	// Helper Methods
	//
	// ##################################################

	public void addProcessTime(ITickProcess process) { this.add(new ProcessSynchHandlers.Time(process)); }

	public void addProcessTimeMax(ITickProcess process) { this.add(new ProcessSynchHandlers.TimeMax(process)); }

	public void addTankFluidId(FluidTank tank) { this.add(new TankSynchHandlers.FluidId(tank)); }

	public void addTankFluidAmount(FluidTank tank) { this.add(new TankSynchHandlers.Amount(tank)); }

	public void addTankFluidCapacity(FluidTank tank) { this.add(new TankSynchHandlers.Capacity(tank)); }
}
