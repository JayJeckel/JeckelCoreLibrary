package jeckelcorelibrary.api.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface ITileInteractable
{
	public void interact(EntityPlayer player, World world, int x, int y, int z, int side);
}
