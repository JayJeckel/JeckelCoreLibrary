package jeckelcorelibrary.base.tiles;

import jeckelcorelibrary.api.processes.ITickProcess;
import jeckelcorelibrary.api.tiles.ITileCustomName;
import jeckelcorelibrary.api.tiles.ITileFrontSide;
import jeckelcorelibrary.api.tiles.ITileName;
import jeckelcorelibrary.api.tiles.ITileProcessor;
import jeckelcorelibrary.api.tiles.ITileTanker;
import jeckelcorelibrary.utils.DirUtil;
import jeckelcorelibrary.utils.FluidUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public abstract class ATileBase
extends TileEntity
implements ITileName, ITileCustomName, ITileFrontSide
{
	public int getX() { return this.xCoord; }
	public int getY() { return this.yCoord; }
	public int getZ() { return this.zCoord; }


	// ##################################################
	//
	// Read and Write NBT
	//
	// ##################################################

	public void readNBTTanks(final NBTTagCompound tagCompound)
	{
		if (this instanceof ITileTanker)
		{
			for (int index = 0; index < ((ITileTanker)this).getTanks().size(); index++)
			{
				final FluidTank tank = ((ITileTanker)this).getTanks().get(index);
				final String name = "tank_" + index;
				FluidUtil.readNBTTank(tank, name, tagCompound);
			}
		}
	}

	public void writeNBTTanks(final NBTTagCompound tagCompound)
	{
		if (this instanceof ITileTanker)
		{
			for (int index = 0; index < ((ITileTanker)this).getTanks().size(); index++)
			{
				final FluidTank tank = ((ITileTanker)this).getTanks().get(index);
				final String name = "tank_" + index;
				FluidUtil.writeNBTTank(tank, name, tagCompound);
			}
		}
	}

	public void readNBTProcesses(final NBTTagCompound tagCompound)
	{
		if (this instanceof ITileProcessor)
		{
			for (final ITickProcess process : ((ITileProcessor)this).getProcesses())
			{
				process.readFromNBT(tagCompound);
			}
		}
	}

	public void writeNBTProcesses(final NBTTagCompound tagCompound)
	{
		if (this instanceof ITileProcessor)
		{
			for (final ITickProcess process : ((ITileProcessor)this).getProcesses())
			{
				process.writeToNBT(tagCompound);
			}
		}
	}

	@Override public void readFromNBT(final NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		this.readNBTTileName(tagCompound);
		this.readNBTCustomName(tagCompound);
		this.readNBTFrontSide(tagCompound);

		this.readNBTTanks(tagCompound);
		this.readNBTProcesses(tagCompound);
	}

	@Override public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		this.writeNBTTileName(tagCompound);
		this.writeNBTCustomName(tagCompound);
		this.writeNBTFrontSide(tagCompound);

		this.writeNBTTanks(tagCompound);
		this.writeNBTProcesses(tagCompound);
	}


	// ##################################################
	//
	// Send and Receive Tile Entity Packet
	//
	// ##################################################

	public void readDataPacket(final NBTTagCompound tagCompound)
	{
		this.readNBTTileName(tagCompound);
		this.readNBTCustomName(tagCompound);
		this.readNBTFrontSide(tagCompound);

		this.readNBTTanks(tagCompound);
		this.readNBTProcesses(tagCompound);
	}

	public void writeDataPacket(final NBTTagCompound tagCompound)
	{
		this.writeNBTTileName(tagCompound);
		this.writeNBTCustomName(tagCompound);
		this.writeNBTFrontSide(tagCompound);

		this.writeNBTTanks(tagCompound);
		this.writeNBTProcesses(tagCompound);
	}

	@Override public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		//this.writeToNBT(tag);
		this.writeDataPacket(tag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
	}

	@Override public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		NBTTagCompound tag = pkt.func_148857_g();
		//this.readFromNBT(tag);
		this.readDataPacket(tag);
	}

	// ##################################################
	//
	// ITileName
	//
	// ##################################################

	private String _tileName = null;

	@Override public boolean hasTileName() { return this._tileName != null && this._tileName.length() > 0; }

	@Override public String getTileName() { return this._tileName; }

	@Override public void setTileName(final String name) { this._tileName = name; }

	@Override public void readNBTTileName(final NBTTagCompound tagCompound)
	{
		if (!this.hasTileName() && tagCompound.hasKey("TileName", 8)) { this._tileName = tagCompound.getString("TileName"); }
	}

	@Override public void writeNBTTileName(final NBTTagCompound tagCompound)
	{
		if (this.hasTileName()) { tagCompound.setString("TileName", this._tileName); }
	}


	// ##################################################
	//
	// ITileCustomName
	//
	// ##################################################

	private String _customName = null;

	@Override public boolean hasCustomName() { return this._customName != null && this._customName.length() > 0; }

	@Override public String getCustomName() { return this._customName; }

	@Override public void setCustomName(final String name) { this._customName = name; }

	@Override public void readNBTCustomName(final NBTTagCompound tagCompound)
	{
		if (tagCompound.hasKey("CustomName", 8)) { this._customName = tagCompound.getString("CustomName"); }
	}

	@Override public void writeNBTCustomName(final NBTTagCompound tagCompound)
	{
		if (this.hasCustomName()) { tagCompound.setString("CustomName", this._customName); }
	}


	// ##################################################
	//
	// ITileFrontSide
	//
	// ##################################################

	private int _frontSide = DirUtil.DEFAULT_FRONT.ordinal();

	@Override public int getFrontSide() { return this._frontSide; }

	@Override public void setFrondSide(final int side) { this._frontSide = side; }
	@Override public void setFrontSide(final World world, final int x, final int y, final int z) { this._frontSide = DirUtil.getDefaultSide(world, x, y, z); }
	@Override public void setFrontSide(final EntityLivingBase living) { this._frontSide = DirUtil.getDefaultSide(living); }

	@Override public void readNBTFrontSide(final NBTTagCompound tagCompound)
	{
		if (tagCompound.hasKey("FrontSide")) { this._frontSide = tagCompound.getInteger("FrontSide"); }
	}

	@Override public void writeNBTFrontSide(final NBTTagCompound tagCompound)
	{
		tagCompound.setInteger("FrontSide", this._frontSide);
	}
}
