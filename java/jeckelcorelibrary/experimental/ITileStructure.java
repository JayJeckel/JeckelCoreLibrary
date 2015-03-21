package jeckelcorelibrary.experimental;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * @deprecated Experimental! Don't use this interface.
 */
@Deprecated
public interface ITileStructure
{
	public int getX();
	public int getY();
	public int getZ();

	/**
	* Stuff the multiblock will do when formed
	*/
	public void updateStructure();

	/**
	* Check that structure is properly formed
	*/
	public boolean validateStructure();

	/**
	* Setup all the blocks in the structure
	*/
	public void setupStructure();

	/**
	* Reset all the parts of the structure
	*/
	public void resetStructure();

	/**
	* Reset method to be run when the master is gone or tells them to
	*/
	public void reset();

	public void writeNBTStructure(NBTTagCompound tagCompound);

	public void readNBTStructure(NBTTagCompound tagCompound);

	public void sendClientUpdateStructure(ITileStructure tile);

	public ITileStructure getMasterTile();

	/**
	* Check that the master exists
	*/
	public boolean doesMasterExist();

	public boolean isStructure();

	public boolean isMaster();

	public int getMasterX();

	public int getMasterY();

	public int getMasterZ();

	public IPatternStructure getPattern();

	public SimpleNetworkWrapper getNetwork();

	public void setStructure(int masterX, int masterY, int masterZ, boolean master);

	public void setStructure(int masterX, int masterY, int masterZ, boolean master, boolean structure);
}
