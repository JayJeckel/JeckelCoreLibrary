package jeckelcorelibrary.base.blocks;

import jeckelcorelibrary.api.tiles.ITileFrontSide;
import jeckelcorelibrary.api.tiles.ITileInteractable;
import jeckelcorelibrary.utils.InvUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ABlockTile extends Block implements ITileEntityProvider
{
	public ABlockTile(String modId, String blockName, Material material, SoundType stepSound)
	{
		super(material);
		this.setStepSound(stepSound);
		this.setBlockName(blockName);
		this.setBlockTextureName(modId + ":" + blockName.replace(".", "/"));
	}

	public boolean hasTransparency() { return this._transparency; }
	public void hasTransparency(boolean value) { this._transparency = value; }
	private boolean _transparency;

	@SideOnly(Side.CLIENT)
	@Override public int getRenderBlockPass() { return (this.hasTransparency() ? 1 : 0); }

	@Override public boolean isOpaqueCube() { return !this.hasTransparency(); }

	//@Override public boolean renderAsNormalBlock() { return true; }


	// ##################################################
	//
	// Block Access
	//
	// ##################################################

	@Override public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || !(tile instanceof ITileInteractable)) { return false; }
		if (!world.isRemote) { ((ITileInteractable)tile).interact(player, world, x, y, z, side); }
		return true;
	}

	@Override public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof ITileFrontSide) { ((ITileFrontSide)tile).setFrontSide(world, x, y, z); }
	}

	@Override public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase living, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, living, stack);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof ITileFrontSide) { ((ITileFrontSide)tile).setFrontSide(living); }
	}

	@Override public void breakBlock(World world, int x, int y, int z, Block block, int blockMetaMaybe)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && tile instanceof IInventory) { InvUtil.dropInventory((IInventory)tile, world, x, y, z); }
		super.breakBlock(world, x, y, z, block, blockMetaMaybe);
		world.removeTileEntity(x, y, z);
	}

	@Override public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventArg)
	{
		super.onBlockEventReceived(world, x, y, z, eventId, eventArg);
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile != null ? tile.receiveClientEvent(eventId, eventArg) : false;
	}
}
