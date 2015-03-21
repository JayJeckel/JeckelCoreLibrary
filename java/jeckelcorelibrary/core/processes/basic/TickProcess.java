package jeckelcorelibrary.core.processes.basic;

import jeckelcorelibrary.api.processes.IProcessCycleHandler;
import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.core.guis.SynchList;
import jeckelcorelibrary.utils.MathUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * A base implementation of the ITickProcess interface.
 *
 * On its own, this process can be used when a modder just needs to
 * keep track of a repeating timed event. Simply update the process
 * and then check if the process has begun or ended and perform
 * whatever actions are needed.
 *
 * More importantly, this class serves as a base for deriving more
 * complex and useful processes. Several protected methods are provided
 * for derived classes to plug into the desired places. In addition,
 * the process-named methods can, and generally should, be overridden
 * to control when and how the process acts.
 *
 * @author JayJeckel
 *
 */
public class TickProcess implements ITickProcess
{
	public TickProcess(final String processName, final int timeMax)
	{
		this._processName = processName;
		this.setTimeMax(timeMax);
	}

	public IProcessCycleHandler getCycleHandler() { return this._cycleHandler; }
	public void setCycleHandler(final IProcessCycleHandler handler) { this._cycleHandler = handler; }
	protected IProcessCycleHandler _cycleHandler = null;


	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	@Override public String getProcessName() { return this._processName; }
	protected String _processName = "";

	public boolean isPersistTimeMax() { return this._persistTimeMax; }
	protected void setPersistTimeMax(final boolean value) { this._persistTimeMax = value; }
	private boolean _persistTimeMax = false;


	// ##################################################
	//
	// Time Methods
	//
	// ##################################################

	@Override public int getTime() { return this._time; }
	@Override public void setTime(final int value) { this._time = value; }
	protected int _time = -1;

	@Override public int getTimeMax() { return this._timeMax; }
	@Override public void setTimeMax(final int value) { this._timeMax = value; }
	protected int _timeMax = -1;

	@Override public int getTimeScaled(final int scale) { return this.getTimeScaled(scale, false); }

	@Override public int getTimeScaled(final int scale, boolean invert) { return MathUtil.getScaledValue(this.getTime(), this.getTimeMax(), scale, invert); }


	// ##################################################
	//
	// Cycle Methods
	//
	// ##################################################

	@Override public boolean isCycleActive() { return this.getTime() > -1; }

	@Override public boolean isCycleBegin() { return this._cycleBegin; }
	protected boolean _cycleBegin = false;

	@Override public boolean isCycleEnd() { return this._cycleEnd; }
	protected boolean _cycleEnd = false;

	@Override public boolean isCycleCancel() { return this._cycleCancel; }
	protected boolean _cycleCancel = false;

	@Override public void cancelCycle(final World world)
	{
		this._cycleCancel = true;
		if (!world.isRemote)
		{
			this.doCycleCancel(world);
			if (this.getCycleHandler() != null) { this.getCycleHandler().handleCycleCancel(); }
			this.resetCycle(world);
		}
	}

	protected void beginCycle(final World world)
	{
		this._cycleBegin = true;
		if (!world.isRemote)
		{
			this.setTime(0);
			this.doCycleBegin(world);
			if (this.getCycleHandler() != null) { this.getCycleHandler().handleCycleBegin(); }
		}
	}

	protected void endCycle(final World world)
	{
		this._cycleEnd = true;
		if (!world.isRemote)
		{
			this.doCycleEnd(world);
			if (this.getCycleHandler() != null) { this.getCycleHandler().handleCycleEnd(); }
			this.resetCycle(world);
		}
	}

	protected void resetCycle(final World world)
	{
		if (!world.isRemote)
		{
			this.setTime(-1);
			this.doCycleReset(world);
			if (this.getCycleHandler() != null) { this.getCycleHandler().handleCycleReset(); }
		}
	}

	/**
	 * Override in derived classes to do something when a cycle begins.
	 * @param world The world the process is operating in.
	 */
	protected void doCycleBegin(final World world) { }

	/**
	 * Override in derived classes to do something when a cycle ends.
	 * @param world The world the process is operating in.
	 */
	protected void doCycleEnd(final World world) { }

	/**
	 * Override in derived classes to do something when a cycle is canceled.
	 * @param world The world the process is operating in.
	 */
	protected void doCycleCancel(final World world) { }

	/**
	 * Override in derived classes to do something when a cycle resets.
	 * This occurs at various points, such as when a cycle ends or is canceled.
	 * @param world The world the process is operating in.
	 */
	protected void doCycleReset(final World world) { }


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	@Override public boolean isProcessing() { return this.isCycleActive(); }

	@Override public boolean isProcessable() { return true; }

	@Override public boolean canProcess() { return this.isProcessable(); }

	@Override public boolean updateProcess(final World world)
	{
		boolean dirty = false;
		this._cycleBegin = false;
		this._cycleEnd = false;
		this._cycleCancel = false;
		if (!this.isProcessable())
		{
			this.cancelCycle(world);
			dirty = true;
		}
		else if (this.canProcess())
		{
			if (this.isProcessing())
			{
				if (this.getTime() == this.getTimeMax())
				{
					this.endCycle(world);
					dirty = true;
				}
				else if (this.getTime() < this.getTimeMax()) { this._time += 1; dirty = true; }
			}
			else
			{
				this.beginCycle(world);
				dirty = true;
			}
		}
		return dirty;
	}


	// ##################################################
	//
	// Read and Write NBT
	//
	// ##################################################

	@Override public void readFromNBT(final NBTTagCompound tagCompound)
	{
		if (tagCompound.hasKey(this.getProcessName()))
		{
			NBTTagCompound tag = tagCompound.getCompoundTag(this.getProcessName());
			this.setTime(tag.getInteger("time"));
			if (this.isPersistTimeMax()) { this.setTimeMax(tag.getInteger("time_max")); }
			this.readNBTProcess(tag);
		}
	}

	@Override public void writeToNBT(final NBTTagCompound tagCompound)
	{
		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("time", this.getTime());
		if (this.isPersistTimeMax()) { tag.setInteger("time_max", this.getTimeMax()); }
		this.writeNBTProcess(tag);

		tagCompound.setTag(this.getProcessName(), tag);
	}

	/**
	 * Override in derived classes to load custom values.
	 * @param tagCompound Tag to load values from.
	 */
	protected void readNBTProcess(final NBTTagCompound tagCompound) { }

	/**
	 * Override in derived classes to save custom values.
	 * @param tagCompound Tag to save values to.
	 */
	protected void writeNBTProcess(final NBTTagCompound tagCompound) { }


	// ##################################################
	//
	// Synch Handler Methods
	//
	// ##################################################

	public void supplySynchHandlers(final SynchList list)
	{
		list.addProcessTime(this);
		if (this.isPersistTimeMax()) { list.addProcessTimeMax(this); }
	}
}
