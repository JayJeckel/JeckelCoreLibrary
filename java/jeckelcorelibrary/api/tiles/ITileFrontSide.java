package jeckelcorelibrary.api.tiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface ITileFrontSide
{
	public int getFrontSide();
	
	public void setFrondSide(int side);
    public void setFrontSide(World world, int x, int y, int z);
    public void setFrontSide(EntityLivingBase living);
    
    public void readNBTFrontSide(NBTTagCompound tagCompound);
    
    public void writeNBTFrontSide(NBTTagCompound tagCompound);
}
