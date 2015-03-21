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

/**
 * The MappedCreativeTab provides more control over how stacks are displayed
 * by the tab by being separated into three groups: blocks, items, and misc.
 *
 * When registered, blocks and items will have their creative tab set the
 * instance they are registered to.
 *
 * All stacks are displayed in the order they are registered. First, stacks are
 * order by the mod id they are registered under, keeping all stacks for a mod
 * grouped together.
 *
 * Within each mod group, stacks are displayed in the order of blocks, then items,
 * then misc stacks. Blocks or items can be registered as misc and those stacks
 * will be displayed in the order they are registered.
 *
 * @author JayJeckel
 *
 */
public class MappedCreativeTab extends ACustomCreativeTab implements ISharedTabRegistry
{
	/**
	 * Standard Constructor
	 * @param title Title of the tab, preferably unlocalized.
	 */
	public MappedCreativeTab(String title)
	{
		super(title);
	}

	/** List of registered mod ids. */
	protected List<String> _listModIds = new ArrayList<String>();

	/** Map of mod ids and list of registered misc stacks. */
	protected Map<String, List<ItemStack>> _mapMisc = new HashMap<String, List<ItemStack>>();

	/** Map of mod ids and list of registered block stacks. */
	protected Map<String, List<ItemStack>> _mapBlocks = new HashMap<String, List<ItemStack>>();

	/** Map of mod ids and list of registered item stacks. */
	protected Map<String, List<ItemStack>> _mapItems = new HashMap<String, List<ItemStack>>();

	/**
	 * Push stack into the related map.
	 * @param modId Id of the mod owning the item.
	 * @param stack Stack to push onto the map.
	 * @param map Map to push the stack onto.
	 */
	protected void pushStack(final String modId, final ItemStack stack, final Map<String, List<ItemStack>> map)
	{
		if (stack == null) { return; }
		if (!this._listModIds.contains(modId)) { this._listModIds.add(modId); }
		if (!map.containsKey(modId)) { map.put(modId, new ArrayList<ItemStack>()); }
		map.get(modId).add(stack.copy());
	}


	// ##################################################
	//
	// Block Methods
	//
	// ##################################################

	@Override public void addBlock(final String modId, final Block block) { this.addBlock(modId, block, 0); }

	@Override public void addBlock(final String modId, final Block block, final int meta)
	{
		if (block == null) { return; }
		this.pushStack(modId, new ItemStack(block, 1, meta), this._mapBlocks);
		block.setCreativeTab(this);
	}


	// ##################################################
	//
	// Item Methods
	//
	// ##################################################

	@Override public void addItem(final String modId, final Item item) { this.addItem(modId, item, 0); }

	@Override public void addItem(final String modId, final Item item, final int meta)
	{
		if (item == null) { return; }
		this.pushStack(modId, new ItemStack(item, 1, meta), this._mapItems);
		item.setCreativeTab(this);
	}


	// ##################################################
	//
	// Misc Methods
	//
	// ##################################################

	@Override public void addMisc(final String modId, final Block block) { this.addMisc(modId, block, 0); }

	@Override public void addMisc(final String modId, final Block block, final int meta)
	{
		if (block == null) { return; }
		this.pushStack(modId, new ItemStack(block, 1, meta), this._mapMisc);
		block.setCreativeTab(this);
	}

	@Override public void addMisc(final String modId, final Item item) { this.addMisc(modId, item, 0); }

	@Override public void addMisc(final String modId, final Item item, final int meta)
	{
		if (item == null) { return; }
		this.pushStack(modId, new ItemStack(item, 1, meta), this._mapMisc);
		item.setCreativeTab(this);
	}


	// ##################################################
	//
	// ACustomCreativeTab Methods
	//
	// ##################################################

	/**
	 * Method to provide all stacks to be displayed in the creative tab.
	 *
	 * Stacks are displayed in the order they were added, grouped by
	 * mod id. For each mod id group stacks are displayed in the order
	 * or blocks, then items, then misc stacks. groups are displayed
	 * in the order they were registered.
	 *
	 * @param stacks List of stacks to be displayed.
	 */
	@SideOnly(Side.CLIENT)
	@Override protected void supplyReleventItems(final List<ItemStack> stacks)
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
