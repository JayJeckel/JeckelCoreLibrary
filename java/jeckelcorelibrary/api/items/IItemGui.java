package jeckelcorelibrary.api.items;

import jeckelcorelibrary.api.guis.IGuiActivatorId;
import jeckelcorelibrary.api.guis.IItemGuiActivator;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @deprecated Use IItemGuiActivator instead.
 */
@Deprecated
public interface IItemGui extends IItemGuiActivator, IGuiActivatorId
{
	public Object createContainer(final EntityPlayer player);

	public Object createScreen(final EntityPlayer player);
}
