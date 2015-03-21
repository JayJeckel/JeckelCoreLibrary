package jeckelcorelibrary.core.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaColored extends Block
{
	public static final String[] colors = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };

	public static ItemStack getDyeStack(int meta)
	{
		if (meta < 0 || meta >= BlockMetaColored.colors.length) { return null; }
		return new ItemStack(Items.dye, 1, 15 - meta);
	}

	@SideOnly(Side.CLIENT)
	private IIcon[] _icons;

	public BlockMetaColored(final Material material)
	{
		super(material);
	}

	public BlockMetaColored(final Material material, final String blockName)
	{
		super(material);
		this.setBlockName(blockName);
		this.setBlockTextureName(blockName);
	}

	public BlockMetaColored(final Material material, final String blockName, final String textureName)
	{
		super(material);
		this.setBlockName(blockName);
		this.setBlockTextureName(textureName);
	}

	@SideOnly(Side.CLIENT)
	@Override public IIcon getIcon(int side, int meta) { return this._icons[meta % this._icons.length]; }

	@Override public int damageDropped(int meta) { return meta; }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int meta = 0; meta < this._icons.length; ++meta) { list.add(new ItemStack(item, 1, meta)); }
	}

	@SideOnly(Side.CLIENT)
	@Override public void registerBlockIcons(IIconRegister register)
	{
		this._icons = new IIcon[BlockMetaColored.colors.length];

		for (int meta = 0; meta < this._icons.length; ++meta)
		{
			this._icons[meta] = register.registerIcon(this.getTextureName() + "_" + BlockMetaColored.colors[meta]);
		}
	}

	@Override public MapColor getMapColor(int meta)
	{
		return MapColor.getMapColorForBlockColored(15 - meta);
	}
}
