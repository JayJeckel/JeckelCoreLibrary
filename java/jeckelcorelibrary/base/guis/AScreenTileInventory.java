package jeckelcorelibrary.base.guis;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class AScreenTileInventory<TTileEntity extends TileEntity> extends AScreenInventory
{
	public AScreenTileInventory(final EntityPlayer player, final IInventory inventory, final Container container, TTileEntity tile, final int xSize, final int ySize)
	{
		super(player, inventory, container, xSize, ySize);
		this._tile = tile;
	}

	protected TTileEntity _tile;
}
