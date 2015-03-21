package jeckelcorelibrary.base.guis;

import jeckelcorelibrary.api.guis.IGuiSynchable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public abstract class AContainerTileInventory<TTileEntity extends TileEntity> extends AContainerInventory
{
	public AContainerTileInventory(final EntityPlayer player, IInventory inventory, TTileEntity tile, final int width, final int height)
	{
		super(player, inventory, width, height);
		this._tile = tile;

		if (this._tile instanceof IGuiSynchable)
		{
			((IGuiSynchable) this._tile).supplySynchHandlers(this._synchList);
		}
	}

	protected TTileEntity _tile;
}
