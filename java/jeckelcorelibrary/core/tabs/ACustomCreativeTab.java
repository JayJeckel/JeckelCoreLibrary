package jeckelcorelibrary.core.tabs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This interface provides a base for derived tabs to directly
 * supply the stacks that the tab should display.
 *
 * @author JayJeckel
 *
 */
public abstract class ACustomCreativeTab extends SimpleCreativeTab
{
	/**
	 * Standard Constructor
	 * @param title Title of the tab, preferably unlocalized.
	 */
	public ACustomCreativeTab(final String title)
	{
		super(title);
	}

	/**
	 * Override supplyReleventItems() method to provide the
	 * stacks that will be passed through this method.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override public void displayAllReleventItems(List list)
	{
		final List<ItemStack> stacks = new ArrayList<ItemStack>();
		this.supplyReleventItems(stacks);
		list.addAll(stacks);
	}

	/**
	 * Method to provide all stacks to be displayed in the creative tab.
	 *
	 * Add stacks to the list in the order they should appear in the tab.
	 *
	 * @param stacks List of stacks to be displayed.
	 */
	@SideOnly(Side.CLIENT)
	protected abstract void supplyReleventItems(final List<ItemStack> stacks);
}
