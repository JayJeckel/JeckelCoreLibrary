package jeckelcorelibrary.api.guis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IBlockGuiActivator extends IGuiActivator
{
	public Object createContainer(final EntityPlayer player, final World world, final int x, final int y, final int z);

	public Object createScreen(final EntityPlayer player, final World world, final int x, final int y, final int z);
}
