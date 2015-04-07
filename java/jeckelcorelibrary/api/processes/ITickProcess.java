package jeckelcorelibrary.api.processes;

import jeckelcorelibrary.core.guis.SynchList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * An interface providing the most fundamental methods for managing a ticking process.
 *
 * The goal of this interface and its related implementing classes is to simplify and
 * reduce the amount of boiler plate code neccessary to handle ticking processes in
 * tile entities, such as burning furnace fuel or converting furnace input into
 * furnace output.
 *
 * By factoring the basic logic and maintaince into interface implementing classes,
 * the chance for copy/paste errors is reduced, the writing of complex tiles
 * is simplified, and the modder can focus on the fun stuff.
 *
 * @author JayJeckel
 *
 */
public interface ITickProcess
{
	// ##################################################
	//
	// General Methods
	//
	// ##################################################

	/**
	 * Each process must have a string name.
	 * Process related values will be stored under
	 * their process's name, so give each process
	 * a unique enough name that it won't conflict
	 * with other processes when reading/writing
	 * entity nbt data.
	 * @return Name of the process.
	 */
	String getProcessName();


	// ##################################################
	//
	// Time Methods
	//
	// ##################################################

	/**
	 * The number of slices that have passed
	 * since this process began the current cycle
	 * or, generally, -1 if the no cycle is running.
	 * @return Slices since current cycle began or, generally, -1.
	 */
	int getTime();

	/**
	 * Set the time value.
	 * This is exposed so the value can be set
	 * after loading or synched across the network.
	 * @param value Value to set.
	 */
	void setTime(final int value);

	/**
	 * The number of slices to run before a cycle is ended.
	 * @return Slices to run before ending a cycle.
	 */
	int getTimeMax();

	/**
	 * Set the max time value.
	 * This is exposed so the value can be set
	 * after loading or synched across the network.
	 * @param value Value to set.
	 */
	void setTimeMax(final int value);

	/**
	 * Scale the current time to the appropriate point between
	 * 0 and the given scale value.
	 *
	 * For example, if time is 80 and timeMax is 240 and scale is 100,
	 * then the returned value would be 33. In other words, since time
	 * is one-third of timeMax, then the number one-third of the way to
	 * the scale value is returned.
	 *
	 * This method is useful filling up progress bars when drawing gui
	 * elements related to the process.
	 * @param scale The value to translate the time/time max ratio onto.
	 * @return The scaled time/time max value.
	 */
	int getTimeScaled(final int scale);

	/**
	 * Scale the current time to the appropriate point between
	 * 0 and the given scale value, optionally inverting the result.
	 *
	 * For example, if time is 80 and timeMax is 240 and scale is 100,
	 * then the returned value would be 33. In other words, since time
	 * is one-third of timeMax, then the number one-third of the way to
	 * the scale value is returned.
	 *
	 * Conversely, the inverted result of the above example would be
	 * 66 instead of 33, as 66 is one-third removed from the scale value.
	 *
	 * This method is useful filling up progress bars when drawing gui
	 * elements related to the process.
	 * @param scale The value to translate the time/time max ratio onto.
	 * @param invert Invert the value ont he time/time max scale.
	 * @return The scaled time value.
	 */
	int getTimeScaled(final int scale, final boolean invert);


	// ##################################################
	//
	// Cycle Methods
	//
	// ##################################################

	/**
	 * Is the process currently cycling.
	 * @return True if cycling.
	 */
	boolean isCycleActive();

	/**
	 * Is this the first slice of a cycle.
	 * @return True if cycle begin.
	 */
	boolean isCycleBegin();

	/**
	 * Is this the last slice of a cycle.
	 * @return True if cycle end.
	 */
	boolean isCycleEnd();

	/**
	 * Was the cycle canceled this slice.
	 * @return True if cycle cancel.
	 */
	boolean isCycleCancel();

	/**
	 * Cancels the current cycle.
	 */
	void cancelCycle(final World world);


	// ##################################################
	//
	// Process Methods
	//
	// ##################################################

	/**
	 * Is the cycle active and can continue.
	 */
	boolean isProcessing();
	/**
	 * Is it possible to begin a cycle.
	 * If this equates to false, then the cycle will be canceled completely.
	 */
	boolean isProcessable();
	/**
	 * Assuming isProcessable is true, can the process begin/continue a cycle.
	 * If this equates to false, then the cycle will not progress, but neither
	 * will it be canceled.
	 */
	boolean canProcess();

	/**
	 * Progress a single slice and run cycle/process logic.
	 * This method should be called each tick in the appropriate
	 * entity/tile entity update method.
	 * @param world The world the process is operating in.
	 * @return True if change occurred.
	 */
	boolean updateProcess(final World world);


	// ##################################################
	//
	// Read and Write NBT
	//
	// ##################################################

	/**
	 * Load process related values.
	 * @param tagCompound Tag to load values from.
	 */
	void readFromNBT(final NBTTagCompound tagCompound);

	/**
	 * Save process related values.
	 * @param tagCompound Tag to save values to.
	 */
	void writeToNBT(final NBTTagCompound tagCompound);


	// ##################################################
	//
	// Synch Handler Methods
	//
	// ##################################################

	void supplySynchHandlers(final SynchList list);
}
