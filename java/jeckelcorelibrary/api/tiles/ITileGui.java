package jeckelcorelibrary.api.tiles;

import jeckelcorelibrary.api.guis.IGuiActivatorId;
import jeckelcorelibrary.api.guis.ITileGuiActivator;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @deprecated Use ITileGuiActivator instead.
 */
@Deprecated
public interface ITileGui extends ITileGuiActivator, IGuiActivatorId
{
	public Object createContainer(final EntityPlayer player);

	public Object createScreen(final EntityPlayer player);
}
