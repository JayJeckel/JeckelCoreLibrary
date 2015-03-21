package jeckelcorelibrary.utils;

import jeckelcorelibrary.core.renderers.ItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Static helper class to centralize game object registering related methods.
 * Copyright (c) 2015 Vinland Solutions
 * Creative Commons Attribution-NonCommercial <http://creativecommons.org/licenses/by-nc/3.0/deed.en_US>
 * @author JayJeckel <http://minecraft.jeckelland.site88.net/>
 */
public class GameRegUtil
{
	/**
	 * Register a block.
	 * @param block Block to register.
	 */
	public static void block(Block block) { GameRegistry.registerBlock(block, block.getUnlocalizedName()); }

	/**
	 * Register a block and optionally associate an item block class and/or a tile entity class.
	 * @param block Block to register.
	 * @param itemClass Optional item block class to associate with the block.
	 * @param tileClass Optional tile entity class to associate with the block.
	 */
	public static void block(Block block,
			Class<? extends ItemBlock> itemClass,
			Class<? extends TileEntity> tileClass)
	{
		if (itemClass == null) { GameRegistry.registerBlock(block, block.getUnlocalizedName()); }
		else { GameRegistry.registerBlock(block, itemClass, block.getUnlocalizedName()); }
		if (tileClass != null) { GameRegistry.registerTileEntity(tileClass, block.getUnlocalizedName()); }
	}

	/**
	 * Register an item.
	 * @param item Item to register.
	 */
	public static void item(Item item)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
	}

	/**
	 * Register a shaped ore recipe.
	 * @param result The output of the recipe.
	 * @param matrix The input pattern of the recipe.
	 */
	public static void recipeShaped(final ItemStack result, Object... matrix)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(result, matrix));
	}

	/**
	 * Static class containing client related registration methods.
	 */
	public static class Client
	{
		/**
		 * Register a tile entity special renderer.
		 * @param block Block to register renderer for.
		 * @param tile Tile entity to register renderer for.
		 * @param renderer Renderer to register.
		 */
		public static void registerTileRenderer(Block block, TileEntity tile, TileEntitySpecialRenderer renderer)
		{
			ClientRegistry.bindTileEntitySpecialRenderer(tile.getClass(), renderer);
			MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(block), new ItemRenderer(renderer, tile));
		}
	}
}
