package jeckelcorelibrary.api.blocks;

import jeckelcorelibrary.api.guis.IBlockGuiActivator;
import jeckelcorelibrary.api.guis.IGuiActivatorId;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @deprecated Use IBlockGuiActivator instead.
 */
@Deprecated
public interface IBlockGui extends IBlockGuiActivator, IGuiActivatorId
{
	public Object createContainer(final EntityPlayer player, final World world, final int x, final int y, final int z);

	public Object createScreen(final EntityPlayer player, final World world, final int x, final int y, final int z);
}
