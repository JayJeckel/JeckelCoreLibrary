package jeckelcorelibrary.api.tiles;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileCustomName
{
	public boolean hasCustomName();

	public String getCustomName();

	public void setCustomName(final String name);

	public void readNBTCustomName(final NBTTagCompound tagCompound);

	public void writeNBTCustomName(final NBTTagCompound tagCompound);
}
