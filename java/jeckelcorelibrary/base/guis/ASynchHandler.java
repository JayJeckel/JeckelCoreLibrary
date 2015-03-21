package jeckelcorelibrary.base.guis;

import jeckelcorelibrary.api.guis.ISynchHandler;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public abstract class ASynchHandler implements ISynchHandler
{
	public ASynchHandler(final int initialValue)
	{
		this.setValue(initialValue);
	}

	/**
	 * Query and return the world value.
	 *
	 * This method must be overriden in derived classes and
	 * should return the value as the game considers it.
	 * For example, if used to synch of the time of a furnace
	 * burning fuel, then it should return the time from the
	 * variable on the furnace tile entity itself.
	 *
	 * @return The world value.
	 */
	protected abstract int queryWorldValue();

	/**
	 * Commit the given value to the world.
	 *
	 * This method must be overriden in derived classes and
	 * should set the given value onto the actual game object
	 * that uses it.
	 * For example, if used to synch the smelting time of a
	 * furnace, then this methods should set the the vairable
	 * on the furnace tile entity itself.
	 *
	 * @param value
	 */
	protected abstract void commitValue(final int value);

	@Override public int getValue() { return this._value; }

	/**
	 * Set the local value.
	 * @param value The new value.
	 */
	protected void setValue(final int value) { this._value = value; }

	/**
	 * The storage variable representing the local value.
	 */
	protected int _value;

	@Override public void send(final int id, final ICrafting crafting, final Container container, final boolean compare)
	{
		final int worldValue = this.queryWorldValue();
		if (!compare || this.getValue() != worldValue)
		{ crafting.sendProgressBarUpdate(container, id, worldValue); }
	}

	@Override public void synch()
	{
		this.setValue(this.queryWorldValue());
	}

	@Override public void commit(final int value)
	{
		this.commitValue(value);
	}
}
