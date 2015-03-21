package jeckelcorelibrary.core.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeckelcorelibrary.utils.BlockUtil;
import jeckelcorelibrary.utils.CoreUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MappedCreativeTab extends ACustomCreativeTab implements ISharedTabRegistry
{
	public MappedCreativeTab(String title)
	{
		super(title);
	}

	private List<String> _listModIds = new ArrayList<String>();
	private Map<String, List<ItemStack>> _mapMisc = new HashMap<String, List<ItemStack>>();
	private Map<String, List<ItemStack>> _mapBlocks = new HashMap<String, List<ItemStack>>();
	private Map<String, List<ItemStack>> _mapItems = new HashMap<String, List<ItemStack>>();

	/**
	 * @deprecated Use addMisc method instead.
	 */
	@Deprecated
	public void add(String modId, ItemStack stack)
	{
		if (stack == null || stack.getItem() == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapMisc.containsKey(modId)) { this._mapMisc.put(modId, new ArrayList<ItemStack>()); }
		this._mapMisc.get(modId).add(stack.copy());
		final Block block = BlockUtil.getBlock(stack);
		if (block != null) { block.setCreativeTab(this); }
		else { stack.getItem().setCreativeTab(this); }
	}

	public void addMisc(final String modId, final Block block) { this.addMisc(modId, block, 0); }

	public void addMisc(final String modId, final Block block, final int meta)
	{
		if (block == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapMisc.containsKey(modId)) { this._mapMisc.put(modId, new ArrayList<ItemStack>()); }
		this._mapMisc.get(modId).add(new ItemStack(block, 1, meta));
		block.setCreativeTab(this);
	}

	public void addMisc(final String modId, final Item item) { this.addMisc(modId, item, 0); }

	public void addMisc(final String modId, final Item item, final int meta)
	{
		if (item == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapMisc.containsKey(modId)) { this._mapMisc.put(modId, new ArrayList<ItemStack>()); }
		this._mapMisc.get(modId).add(new ItemStack(item, 1, meta));
		item.setCreativeTab(this);
	}

	public void addMisc(String modId, ItemStack stack)
	{
		if (stack == null || stack.getItem() == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapMisc.containsKey(modId)) { this._mapMisc.put(modId, new ArrayList<ItemStack>()); }
		this._mapMisc.get(modId).add(stack.copy());
		final Block block = BlockUtil.getBlock(stack);
		if (block != null) { block.setCreativeTab(this); }
		else { stack.getItem().setCreativeTab(this); }
	}

	public void addBlock(String modId, ItemStack stack)
	{
		if (stack == null || stack.getItem() == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapBlocks.containsKey(modId)) { this._mapBlocks.put(modId, new ArrayList<ItemStack>()); }
		this._mapBlocks.get(modId).add(stack.copy());
		final Block block = BlockUtil.getBlock(stack);
		if (block != null) { block.setCreativeTab(this); }
	}

	public void addItem(String modId, ItemStack stack)
	{
		if (stack == null || stack.getItem() == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapItems.containsKey(modId)) { this._mapItems.put(modId, new ArrayList<ItemStack>()); }
		this._mapItems.get(modId).add(stack.copy());
		stack.getItem().setCreativeTab(this);
	}

	@SideOnly(Side.CLIENT)
	@Override protected void supplyReleventItems(List<ItemStack> stacks)
	{
		List<String> modIds = CoreUtil.asSortedList(this._listModIds);
		for (String modId : modIds)
		{
			if (this._mapBlocks.containsKey(modId))
			{
				for (ItemStack stack : this._mapBlocks.get(modId))
				{
					stack.getItem().getSubItems(stack.getItem(), this, stacks);
				}
			}
			if (this._mapItems.containsKey(modId))
			{
				for (ItemStack stack : this._mapItems.get(modId))
				{
					stack.getItem().getSubItems(stack.getItem(), this, stacks);
				}
			}
			if (this._mapMisc.containsKey(modId))
			{
				for (ItemStack stack : this._mapMisc.get(modId))
				{
					stack.getItem().getSubItems(stack.getItem(), this, stacks);
				}
			}
		}

		//Block block;
		//Item item;

		/*block = BarrelBlocks.barrel_wood;
		item = Item.getItemFromBlock(block);
		block.getSubBlocks(item, this, list);

		item = LocalItems.nugget_iron;
		item.getSubItems(item, this, list);*/

		if (this.func_111225_m() != null)
		{
			this.addEnchantmentBooksToList(stacks, this.func_111225_m());
		}
	}
}
