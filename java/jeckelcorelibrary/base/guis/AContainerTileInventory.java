package jeckelcorelibrary.base.guis;

import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.api.tiles.ITileProcessor;
import jeckelcorelibrary.api.tiles.ITileTanker;
import jeckelcorelibrary.utils.FluidUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;

public abstract class AContainerTileInventory<TTileEntity extends TileEntity> extends AContainerInventory
{
	public AContainerTileInventory(final EntityPlayer player, final IInventory inventory, final TTileEntity tile, final int width, final int height)
	{
		super(player, inventory, width, height);
		this._tile = tile;

		if (this._tile instanceof ITileTanker)
		{
			for (final FluidTank tank : ((ITileTanker) this._tile).getTanks())
			{
				FluidUtil.supplySynchHandlers(tank, this._synchList);
			}
		}

		if (this._tile instanceof ITileProcessor)
		{
			for (final ITickProcess process : ((ITileProcessor) this._tile).getProcesses())
			{
				process.supplySynchHandlers(this._synchList);
			}

			//((ITileProcessor) this._tile).supplySynchHandlers(this._synchList);
		}
	}

	protected TTileEntity _tile;
}
