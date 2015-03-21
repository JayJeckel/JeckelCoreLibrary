package jeckelcorelibrary.core.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaWooden extends Block
{
	public static final String[] woods = { "oak", "spruce", "birch", "jungle", "acacia", "big_oak" };

	public static ItemStack getPlankStack(int meta)
	{
		if (meta < 0 || meta >= BlockMetaWooden.woods.length) { return null; }
		return new ItemStack(Blocks.planks, 1, meta);
	}

	public static ItemStack getLogStack(int meta)
	{
		if (meta < 0 || meta >= woods.length) { return null; }
		else if (meta < 5) { return new ItemStack(Blocks.log, 1, meta); }
		else { return new ItemStack(Blocks.log2, 1, meta - 5); }
	}

	@SideOnly(Side.CLIENT)
	private IIcon[] _icons;

	public BlockMetaWooden()
	{
		super(Material.wood);
		this.setStepSound(Block.soundTypeWood);
		this.setHardness(2.5F);
	}

	public BlockMetaWooden(final String blockName)
	{
		this();
		this.setBlockName(blockName);
		this.setBlockTextureName(blockName);
	}

	public BlockMetaWooden(final String blockName, final String textureName)
	{
		this();
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
		this._icons = new IIcon[BlockMetaWooden.woods.length];

		for (int meta = 0; meta < this._icons.length; ++meta)
		{
			this._icons[meta] = register.registerIcon(this.getTextureName() + "_" + BlockMetaWooden.woods[meta]);
		}
	}
}
