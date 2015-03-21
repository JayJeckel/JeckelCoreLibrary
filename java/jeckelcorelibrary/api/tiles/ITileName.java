package jeckelcorelibrary.api.tiles;

import net.minecraft.nbt.NBTTagCompound;


public interface ITileName
{
	public boolean hasTileName();

	public String getTileName();

	public void setTileName(final String name);

	public void readNBTTileName(final NBTTagCompound tagCompound);

	public void writeNBTTileName(final NBTTagCompound tagCompound);
}
