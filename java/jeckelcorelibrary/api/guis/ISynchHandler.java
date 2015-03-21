package jeckelcorelibrary.api.guis;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public interface ISynchHandler
{
	/**
	 * Get the currently set local value.
	 * @return The local value.
	 */
	public int getValue();

	/**
	 * Send the local value between the client and server.
	 * If compare is true, then the value will only be sent
	 * if it differs from the world value.
	 *
	 * This method should be called from Container.addCraftingToCrafters()
	 * with false passed to the compare parameter.
	 * This method should also be called from inside the for-loop of
	 * Container.detectAndSendChanges() with true passed to the compare parameter.
	 *
	 * @param id Unique id number of the synch packet.
	 * @param crafting The ICrafting instance being worked with.
	 * @param container The container representing the world object.
	 * @param compare Should the synch be sent only if the local and world values to not match.
	 */
	public void send(final int id, final ICrafting crafting, final Container container, final boolean compare);

	/**
	 * Synchronize the local value to the world value.
	 * If the values differ, the world value overrides
	 * the local value.
	 *
	 * This method should be called after the for-loop in Container.detectAndSendChanges().
	 */
	public void synch();

	/**
	 * Commit the given value to the world.
	 * If the values differ, the local value overrides
	 * the world value.
	 *
	 * This method should be called from Container.updateProgressBar().
	 *
	 * @param value The value to commit.
	 */
	public void commit(final int value);
}
