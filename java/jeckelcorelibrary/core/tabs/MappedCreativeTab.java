package jeckelcorelibrary.core.tabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


	// ##################################################
	//
	// addMisc Methods
	//
	// ##################################################

	protected void pushMisc(String modId, ItemStack stack)
	{
		if (stack == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapMisc.containsKey(modId)) { this._mapMisc.put(modId, new ArrayList<ItemStack>()); }
		this._mapMisc.get(modId).add(stack.copy());
	}

	@Override public void addMisc(final String modId, final Block block) { this.addMisc(modId, block, 0); }

	@Override public void addMisc(final String modId, final Block block, final int meta)
	{
		if (block == null) { return; }
		this.pushMisc(modId, new ItemStack(block, 1, meta));
		block.setCreativeTab(this);
	}

	@Override public void addMisc(final String modId, final Item item) { this.addMisc(modId, item, 0); }

	@Override public void addMisc(final String modId, final Item item, final int meta)
	{
		if (item == null) { return; }
		this.pushMisc(modId, new ItemStack(item, 1, meta));
		item.setCreativeTab(this);
	}


	// ##################################################
	//
	// addMisc Methods
	//
	// ##################################################

	protected void pushBlock(String modId, ItemStack stack)
	{
		if (stack == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapBlocks.containsKey(modId)) { this._mapBlocks.put(modId, new ArrayList<ItemStack>()); }
		this._mapBlocks.get(modId).add(stack.copy());
	}

	@Override public void addBlock(final String modId, final Block block) { this.addBlock(modId, block, 0); }

	@Override public void addBlock(final String modId, final Block block, final int meta)
	{
		if (block == null) { return; }
		this.pushBlock(modId, new ItemStack(block, 1, meta));
		block.setCreativeTab(this);
	}


	// ##################################################
	//
	// addMisc Methods
	//
	// ##################################################

	protected void pushItem(String modId, ItemStack stack)
	{
		if (stack == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!this._mapItems.containsKey(modId)) { this._mapItems.put(modId, new ArrayList<ItemStack>()); }
		this._mapBlocks.get(modId).add(stack.copy());
	}

	@Override public void addItem(final String modId, final Item item) { this.addItem(modId, item, 0); }

	@Override public void addItem(final String modId, final Item item, final int meta)
	{
		if (item == null) { return; }
		this.pushMisc(modId, new ItemStack(item, 1, meta));
		item.setCreativeTab(this);
	}


	// ##################################################
	//
	// ACustomCreativeTab Methods
	//
	// ##################################################

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

		if (this.func_111225_m() != null)
		{
			this.addEnchantmentBooksToList(stacks, this.func_111225_m());
		}
	}
}
