package jeckelcorelibrary.base.tiles;

import jeckelcorelibrary.api.tiles.ITileCustomName;
import jeckelcorelibrary.api.tiles.ITileFrontSide;
import jeckelcorelibrary.api.tiles.ITileName;
import jeckelcorelibrary.utils.DirUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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

	@Override public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);

		this.readNBTTileName(tagCompound);
		this.readNBTCustomName(tagCompound);
		this.readNBTFrontSide(tagCompound);
	}

	@Override public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);

		this.writeNBTTileName(tagCompound);
		this.writeNBTCustomName(tagCompound);
		this.writeNBTFrontSide(tagCompound);
	}


	// ##################################################
	//
	// Send and Receive Tile Entity Packet
	//
	// ##################################################

	@Override public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
	}

	@Override public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		NBTTagCompound tag = pkt.func_148857_g();
		this.readFromNBT(tag);
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
