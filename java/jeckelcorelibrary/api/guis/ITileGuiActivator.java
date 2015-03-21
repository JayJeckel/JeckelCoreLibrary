package jeckelcorelibrary.api.guis;

import net.minecraft.entity.player.EntityPlayer;

public interface ITileGuiActivator extends IGuiActivator
{
	public Object createContainer(final EntityPlayer player);

	public Object createScreen(final EntityPlayer player);
}
