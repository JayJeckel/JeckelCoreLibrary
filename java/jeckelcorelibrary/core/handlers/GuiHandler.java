package jeckelcorelibrary.core.handlers;

import jeckelcorelibrary.api.blocks.IBlockGui;
import jeckelcorelibrary.api.guis.IBlockGuiActivator;
import jeckelcorelibrary.api.guis.IItemGuiActivator;
import jeckelcorelibrary.api.guis.ITileGuiActivator;
import jeckelcorelibrary.api.items.IItemGui;
import jeckelcorelibrary.api.tiles.ITileGui;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * An implementation of IGuiHandler that removes the need to keep track
 * of unique gui id numbers. TileEntity, Block, and Item classes can
 * implement the appropriate related interface and after the mod registers
 * the instance of GuiHandler, then gui's will work.
 *
 * @author Jeckel
 *
 */
public class GuiHandler implements IGuiHandler
{
	/**
	 * Returns a Server side gui element to be displayed to the user.
	 * Only called on the Server side and will return a Container instance.
	 *
	 * The proper gui element is chosen by checking three places for specific interfaces.
	 *
	 * First, the item held by the player is checked for the IItemGuiActivator. If found,
	 * the held item will have its createContainer method called to return the actual
	 * Container instance.
	 *
	 * Second, the same process is applied to the block at the given x, y, and z arguments.
	 * If that block exists and implements the IBlockGuiActivator interface, then the block
	 * has its createContainer method called for the Container instance.
	 *
	 * Third, the tile entity at the given coordinates, if one exists, will
	 * be checked for the ITileGuiActivator interface and, if so, will have the
	 * appropriate method called and the Container returned.
	 *
	 * If none of the conditions above are met, then null will be returned.
	 *
	 * @param ID The Gui ID Number
	 * @param player The player viewing the Gui
	 * @param world The current world
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z Position
	 * @return A Container instance or null.
	 */
	@Override public Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		{
			final ItemStack stack = player.getHeldItem();
			final Item item = (stack == null ? null : stack.getItem());
			if (item != null && (item instanceof IItemGui || item instanceof IItemGuiActivator))
			{
				return ((IItemGuiActivator)item).createContainer(player);
			}
		}

		{
			final Block block = world.getBlock(x, y, z);
			if (block != null && (block instanceof IBlockGui || block instanceof IBlockGuiActivator))
			{
				return ((IBlockGuiActivator)block).createContainer(player, world, x, y, z);
			}
		}

		{
			final TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && (tile instanceof ITileGui || tile instanceof ITileGuiActivator))
			{
				return ((ITileGuiActivator)tile).createContainer(player);
			}
		}

		return null;
	}

	/**
	 * Returns a Server or Client side gui element to be displayed to the user.
	 * When called on the Server side will return an instance of Container.
	 * When called from the Client side will return an instance of GuiScreen.
	 *
	 * The proper gui element is chosen by checking three places for specific interfaces.
	 *
	 * First, the item held by the player is checked for the IItemGuiActivator. If found,
	 * the held item will have its createContainer/createScreen method called to return
	 * the actual Container/GuiScreen instance.
	 *
	 * Second, the same process is applied to the block at the given x, y, and z arguments.
	 * If that block exists and implements the IBlockGuiActivator interface, then the block
	 * has its createContainer/createScreen method called for the Container/GuiScreen instance.
	 *
	 * Third, the tile entity at the given coordinates, if one exists, will
	 * be checked for the ITileGuiActivator interface and, if so, will have the
	 * appropriate method called and the Container/GuiScreen returned.
	 *
	 * If none of the conditions above are met, then null will be returned.
	 *
	 * @param ID The Gui ID Number
	 * @param player The player viewing the Gui
	 * @param world The current world
	 * @param x X Position
	 * @param y Y Position
	 * @param z Z Position
	 * @return A Container (Server side) or GuiScreen (Client side) instance or null.
	 */
	@Override public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z)
	{
		{
			final ItemStack stack = player.getHeldItem();
			final Item item = (stack == null ? null : stack.getItem());
			if (item != null && (item instanceof IItemGui || item instanceof IItemGuiActivator))
			{
				if (world.isRemote) { return ((IItemGuiActivator)item).createScreen(player); }
				else { return ((IItemGuiActivator)item).createContainer(player); }
			}
		}

		{
			final Block block = world.getBlock(x, y, z);
			if (block != null && (block instanceof IBlockGui || block instanceof IBlockGuiActivator))
			{
				if (world.isRemote) { return ((IBlockGuiActivator)block).createScreen(player, world, x, y, z); }
				else { return ((IBlockGuiActivator)block).createContainer(player, world, x, y, z); }
			}
		}

		{
			final TileEntity tile = world.getTileEntity(x, y, z);
			if (tile != null && (tile instanceof ITileGui || tile instanceof ITileGuiActivator))
			{
				if (world.isRemote) { return ((ITileGuiActivator)tile).createScreen(player); }
				else { return ((ITileGuiActivator)tile).createContainer(player); }
			}
		}

		return null;
	}
}
